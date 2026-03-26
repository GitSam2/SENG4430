package DependencyTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.example.services.dependencies.DependencyTree;
import org.example.services.dependencies.DependencyTreeResolver;
import org.example.services.dependencies.MavenBootstrap;
import org.junit.jupiter.api.Test;

public class DependencyTreeResolverTest {
    @Test
    void testResolveDependencies() {
        RepositorySystem repositorySystem = MavenBootstrap.newRepositorySystem();
        RepositorySystemSession repositorySystemSession = MavenBootstrap.newSession(repositorySystem);

        DependencyTreeResolver resolver = new DependencyTreeResolver(repositorySystem, repositorySystemSession);
        String projectPath = "pom.xml"; // Assuming this test is run in a directory with a pom.xml file

        List<DependencyTree> trees = null;
        try {
            trees = resolver.resolvePom(projectPath);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false, "Pom resolution threw an exception: " + e.getMessage());
        }

        assertNotNull(trees);
        assertFalse(trees.isEmpty());

        DependencyTree rootTree = trees.get(0);
        assertNotNull(rootTree);
        assertNotNull(rootTree.model());
    }
}
