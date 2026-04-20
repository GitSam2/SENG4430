package org.example.services.dependencies;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.*;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.repository.RemoteRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DependencyTreeResolver {

    private final RepositorySystem repoSystem;
    private final RepositorySystemSession session;
    public volatile int totalModules = 0;
    public volatile int resolvedModules = 0;

    public DependencyTreeResolver(RepositorySystem repoSystem,
                                  RepositorySystemSession session) {
        this.repoSystem = repoSystem;
        this.session = session;
    }

    /// Resolves the dependencies of a POM file and returns a list of dependency trees for each direct dependency.
    /// @param path The file path to the POM file.
    /// @return A list of DependencyTree objects representing the dependency trees for each direct dependency.
    public List<DependencyTree> resolvePom(String path) throws ModelBuildingException, DependencyCollectionException {
        return resolvePom(path, new ArrayList<String>());
    }

    /// Resolves the dependencies of a POM file and returns a list of dependency trees for each direct dependency.
    /// @param path The file path to the POM file.
    /// @return A list of DependencyTree objects representing the dependency trees for each direct dependency.
    public List<DependencyTree> resolvePom(String path, List<String> visited) throws ModelBuildingException, DependencyCollectionException {
        if (visited.contains(path)) {
            return new ArrayList<>();
        }
        visited.add(path);

        ModelBuildingRequest request = new DefaultModelBuildingRequest();
        request.setPomFile(new File(path));
        request.setSystemProperties(System.getProperties());
        request.setProcessPlugins(false);
        request.setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MINIMAL);

        List<RemoteRepository> remoteRepos = new ArrayList<>();
        remoteRepos.add(new RemoteRepository.Builder("central", "default",
                "https://repo1.maven.org/maven2").build());

        // Wire in the resolver so the model builder can fetch parent POMs from remote
        request.setModelResolver(new ProjectModelResolver(
                session, null, repoSystem, null, remoteRepos,
                null, null));

        ModelBuildingResult result = new DefaultModelBuilderFactory()
                .newInstance()
                .build(request);
        result.getProblems().forEach(p -> {
            if (p.getSeverity() == ModelProblem.Severity.ERROR ||
                p.getSeverity() == ModelProblem.Severity.FATAL) {
                System.err.println(String.format("Model problem [%s]: %s", p.getSeverity(), p.getMessage()));
            } else {
                System.out.println(String.format("Model hint [%s]: %s", p.getSeverity(), p.getMessage()));
            }
        });

        Model model = result.getEffectiveModel();

        // Get direct dependencies
        List<DependencyTree> dependencies = new ArrayList<>();
        for (Dependency dep : model.getDependencies()) {
            dependencies.add(resolveDependency(model, dep));
        }

        // Get dependencies of internal modules if it's a multi-module project
        if (model.getModules() != null) {
            totalModules += model.getModules().size();
            for (String modulePath : model.getModules()) {
                String fullPath = new File(path).getParent() + File.separator + modulePath + File.separator + "pom.xml";
                dependencies.addAll(resolvePom(fullPath, visited));
            }
        }

        resolvedModules += 1;
        return dependencies;
    }

    // private 



    /// Resolves the dependencies of a specific module (dependency) and returns its dependency tree.
    /// @param model The Maven model representing the POM file.
    /// @param dependency The specific dependency for which to resolve the dependency tree.
    /// @return A DependencyTree object representing the dependency tree for the specified module.
    public DependencyTree resolveDependency(Model model, Dependency dependency) throws DependencyCollectionException {
        Artifact artifact = new DefaultArtifact(
                dependency.getGroupId(),
                dependency.getArtifactId(),
                "jar",
                dependency.getVersion()
        );

        CollectRequest collect = new CollectRequest();
        collect.setRoot(new org.eclipse.aether.graph.Dependency(artifact, "compile"));
        collect.setRepositories(MavenBootstrap.defaultRepositories());

        CollectResult collectResult = repoSystem.collectDependencies(session, collect);

        return new DependencyTree(model, collectResult.getRoot());
    }
}
