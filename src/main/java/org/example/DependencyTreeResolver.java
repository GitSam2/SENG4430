package org.example;

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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DependencyTreeResolver {

    private final RepositorySystem repoSystem;
    private final RepositorySystemSession session;

    public DependencyTreeResolver(RepositorySystem repoSystem,
                                  RepositorySystemSession session) {
        this.repoSystem = repoSystem;
        this.session = session;
    }

    public List<DependencyTree> resolvePom(String path) throws ModelBuildingException, DependencyCollectionException {
        ModelBuildingRequest request = new DefaultModelBuildingRequest();
        request.setPomFile(new File(path));
        request.setProcessPlugins(false);
        request.setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MINIMAL);

        ModelBuildingResult result = new DefaultModelBuilderFactory()
                .newInstance()
                .build(request);

        Model model = result.getEffectiveModel();

        List<DependencyTree> dependencies = new ArrayList<>();
        for (Dependency dep : model.getDependencies()) {
            dependencies.add(resolveModule(model, dep));
        }

        return dependencies;
    }

    public DependencyTree resolveModule(Model model, Dependency dependency) throws DependencyCollectionException {
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
