package org.example.services.dependencies;

import org.apache.maven.model.Parent;
import org.apache.maven.model.Repository;
import org.apache.maven.model.building.FileModelSource;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.model.resolution.InvalidRepositoryException;
import org.apache.maven.model.resolution.ModelResolver;
import org.apache.maven.model.resolution.UnresolvableModelException;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.impl.RemoteRepositoryManager;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolves parent POMs and imported BOMs from remote repositories during
 * effective-model building.
 *
 * <p>Maven's model builder calls this whenever it encounters a {@code <parent>}
 * element or a {@code <scope>import</scope>} dependency, so those POMs can be
 * fetched and merged into the effective model.
 */
public class ProjectModelResolver implements ModelResolver {

    private static final Logger log = LoggerFactory.getLogger(ProjectModelResolver.class);

    private final RepositorySystemSession  session;
    private final RequestTrace             trace;
    private final RepositorySystem         system;
    private final RemoteRepositoryManager  remoteRepositoryManager;
    private final List<RemoteRepository>   repositories;

    public ProjectModelResolver(RepositorySystemSession session,
                                RequestTrace trace,
                                RepositorySystem system,
                                RemoteRepositoryManager remoteRepositoryManager,
                                List<RemoteRepository> repositories,
                                Object projectBuildingHelper,  // unused, kept for API compat
                                Object reactorModelPool) {      // unused, kept for API compat
        this.session                = session;
        this.trace                  = trace;
        this.system                 = system;
        this.remoteRepositoryManager = remoteRepositoryManager;
        this.repositories           = new ArrayList<>(repositories);
    }

    // Copy constructor used by clone()
    private ProjectModelResolver(ProjectModelResolver original) {
        this.session                = original.session;
        this.trace                  = original.trace;
        this.system                 = original.system;
        this.remoteRepositoryManager = original.remoteRepositoryManager;
        this.repositories           = new ArrayList<>(original.repositories);
    }

    // ── ModelResolver API ──────────────────────────────────────────────────

    @Override
    public ModelSource resolveModel(String groupId, String artifactId, String version)
            throws UnresolvableModelException {
        return resolve(groupId, artifactId, version, "pom");
    }

    @Override
    public ModelSource resolveModel(Parent parent) throws UnresolvableModelException {
        log.debug("Resolving parent POM: {}:{}:{}", parent.getGroupId(),
                parent.getArtifactId(), parent.getVersion());
        return resolve(parent.getGroupId(), parent.getArtifactId(), parent.getVersion(), "pom");
    }

    @Override
    public ModelSource resolveModel(
            org.apache.maven.model.Dependency dependency) throws UnresolvableModelException {
        log.debug("Resolving BOM dependency: {}:{}:{}",
                dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion());
        return resolve(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(), "pom");
    }

    @Override
    public void addRepository(Repository repository) throws InvalidRepositoryException {
        addRepository(repository, false);
    }

    @Override
    public void addRepository(Repository repository, boolean replace) throws InvalidRepositoryException {
        boolean exists = repositories.stream().anyMatch(r -> r.getId().equals(repository.getId()));
        if (!exists || replace) {
            if (exists) repositories.removeIf(r -> r.getId().equals(repository.getId()));
            repositories.add(new RemoteRepository.Builder(
                    repository.getId(), "default", repository.getUrl()).build());
            log.debug("Added repository: {} → {}", repository.getId(), repository.getUrl());
        }
    }

    @Override
    public ModelResolver newCopy() {
        return new ProjectModelResolver(this);
    }

    // ── Resolution logic ───────────────────────────────────────────────────

    private ModelSource resolve(String groupId, String artifactId, String version, String extension)
            throws UnresolvableModelException {
        Artifact artifact = new DefaultArtifact(groupId, artifactId, "", extension, version);
        ArtifactRequest request = new ArtifactRequest(artifact, repositories, null);
        request.setTrace(trace);

        try {
            ArtifactResult result = system.resolveArtifact(session, request);
            return new FileModelSource(result.getArtifact().getFile());
        } catch (ArtifactResolutionException e) {
            throw new UnresolvableModelException(
                    "Could not resolve " + groupId + ":" + artifactId + ":" + version + " — " + e.getMessage(),
                    groupId, artifactId, version, e);
        }
    }
}
