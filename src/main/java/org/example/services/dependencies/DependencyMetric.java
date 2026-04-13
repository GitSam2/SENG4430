package org.example.services.dependencies;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
         // Kick off compute in a background thread
        CompletableFuture<DependencyResult> future = CompletableFuture.supplyAsync(() -> computeResult(ctx));

        // Render loop on the main thread
        int tick = 0;
        String[] throbber = { "|", "/", "-", "\\" };

        while (!future.isDone()) {
            String spinner = throbber[tick % throbber.length];
            String status  = getStatus();

            // \r rewrites the current line in a TUI
            System.out.print("\r" + spinner + "  " + status + "   ");
            System.out.flush();

            tick++;
            try {
                Thread.sleep(100); // poll every 100ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        DependencyResult result = null;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            Console.error("An error occurred while computing the dependency metric.");
        }

        // TODO: move description somewhere else, this is just for testing
        System.out.print("\r");
        System.out.flush();
        if (result != null) {
            System.out.println(Console.bold("Transitive") + " dependencies are dependencies that are not directly included in the pom.xml but are dependencies of the direct dependencies.\n");
            System.out.println(Console.bold("CVE") + " stands for Common Vulnerabilities and Exposures. CVEs are publicly disclosed cybersecurity vulnerabilities.\n");
            System.out.println(Console.bold("CVSS Score") + " stands for Common Vulnerability Scoring System. A standardised score (from 0 to 10) that indicates the severity of a CVE. A higher score indicates a more severe vulnerability.\n");
            System.out.println(Console.divider('─'));
        }

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

    private DependencyResult computeResult(MetricContext ctx) {
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

    private String getStatus() {
        if (fetchingCves)       return "Fetching CVE information for dependencies...";
        if (resolving)          return "Resolving dependency tree...";
        if (bootstrapping)      return "Bootstrapping maven...";
        
        return "Starting...";
    }
}