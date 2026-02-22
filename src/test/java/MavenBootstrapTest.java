import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.graph.Dependency;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.eclipse.aether.RepositorySystem;

import org.example.MavenBootstrap;

@DisplayName("Test Maven resolver base classes")
public class MavenBootstrapTest {

    RepositorySystem repositorySystem = MavenBootstrap.newRepositorySystem();
    RepositorySystemSession repositorySystemSession = MavenBootstrap.newSession(repositorySystem);

    @Test
    @DisplayName("Test that Maven resolver can resolve a known dependency (spring-boot)")
    void testResolveKnownDependency() {
        Artifact artifact = new DefaultArtifact(
                "org.springframework.boot",
                "spring-boot",
                "jar",
                "4.0.3"
        );

        CollectRequest collect = new CollectRequest();
        collect.setRoot(new Dependency(artifact, "compile"));
        collect.setRepositories(MavenBootstrap.defaultRepositories());

        assertDoesNotThrow(() -> {
            CollectResult result = repositorySystem.collectDependencies(repositorySystemSession, collect);
        });
    }
}
