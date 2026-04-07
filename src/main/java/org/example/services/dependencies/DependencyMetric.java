package org.example.services.dependencies;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.graph.DependencyNode;
import org.example.picocli.Console;
import org.example.services.Metric;
import org.example.services.MetricContext;

public class DependencyMetric implements Metric<DependencyResult> {
    public volatile boolean bootstrapping = false;
    public volatile boolean resolving = false;
    public volatile boolean fetchingCves = false;

    @Override
    public String id() {
        return "Dependency Metric";
    }

    @Override
    public DependencyResult compute(MetricContext ctx) {
        bootstrapping = true;
        // Bootstrap the repo
        RepositorySystem repositorySystem = MavenBootstrap.newRepositorySystem();
        RepositorySystemSession repositorySystemSession = MavenBootstrap.newSession(repositorySystem);

        resolving = true;
        // Resolve the dependency tree
        DependencyTreeResolver resolver = new DependencyTreeResolver(repositorySystem, repositorySystemSession);
        Path projectPath = ctx.getProjectPath();

        Path pomPath = projectPath.resolve("pom.xml");
        if (!pomPath.toFile().exists()) {
            Console.error("Project did not contain a pom.xml file");
            return null;
        }

        fetchingCves = true;

        // Fetch CVE information for dependencies
        List<DependencyTree> trees = null;
        try {
            trees = resolver.resolvePom(pomPath.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Console.error("Project did not contain a pom.xml file or it could not be resolved");
            return null;
        }

        List<NodeResult> nodeResults = new ArrayList<>();
        for (DependencyTree tree : trees) {
            try {
                nodeResults.addAll(nodeVisitor(tree.root(), true));
            } catch (IOException | InterruptedException e) {
                Console.error("Could not fetch CVE information for dependencies.");
                return null;
            }
        }

        DependencyResult result = new DependencyResult(nodeResults); // Placeholder, should be populated with actual
                                                                     // results from nodeVisitor

        return result;
    }

    private List<NodeResult> nodeVisitor(DependencyNode node, boolean isDirectDependency)
            throws IOException, InterruptedException {
        CveService cveService = new CveService();
        List<NodeResult> results = new ArrayList<>();

        List<CveInfo> cveInfos = cveService.fetchCves(new DependencyModel(node.getArtifact().getArtifactId(),
                node.getArtifact().getGroupId(), node.getArtifact().getVersion()));

        boolean hasCve = !cveInfos.isEmpty();
        String fixedVersion = fixedVersion(cveInfos);
        double severity = severity(cveInfos);

        NodeResult result = new NodeResult(node.getArtifact().getArtifactId() + ":" + node.getArtifact().getVersion(),
                isDirectDependency, hasCve, severity, fixedVersion);
        results.add(result);

        for (DependencyNode child : node.getChildren()) {
            // Visit children of node
            results.addAll(nodeVisitor(child, false));
        }

        return results;
    }

    private String fixedVersion(List<CveInfo> cveInfos) {
        String newestVersion = null;
        for (CveInfo info : cveInfos) {
            if (info.fixedVersion() != null) {
                if (newestVersion == null || compareVersions(info.fixedVersion(), newestVersion) > 0) {
                    newestVersion = info.fixedVersion();
                }
            }
        }
        return newestVersion;
    }

    private int compareVersions(String v1, String v2) {
        ComparableVersion v1v = new ComparableVersion(v1);
        ComparableVersion v2v = new ComparableVersion(v2);
        return v2v.compareTo(v1v);
    }

    private double severity(List<CveInfo> cveInfos) {
        double maxSeverity = 0.0;
        for (CveInfo info : cveInfos) {
            if (info.cvsScore() > maxSeverity) {
                maxSeverity = info.cvsScore();
            }
        }
        return maxSeverity;
    }
}