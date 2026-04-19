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
    private volatile DependencyTreeResolver resolver = null;
    private volatile int totalDependencies = 0;
    private volatile int fetchedDependencies = 0;
    private volatile ArrayList<String> visitedDependencies = new ArrayList<>();

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
            return null;
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

    /**
     * Recursively visits each node in the dependency tree, fetching CVE information for each dependency and its children. This method is called for each node in the tree, starting with the root nodes (direct dependencies) and then visiting their children (transitive dependencies).
     * @param node
     * @param isDirectDependency
     * @return A list of NodeResult objects containing information about each dependency, including whether it is a direct dependency, whether it has associated CVEs, the severity of those CVEs, and any fixed versions available.
     * @throws IOException
     * @throws InterruptedException
     */
    private List<NodeResult> nodeVisitor(DependencyNode node, boolean isDirectDependency)
            throws IOException, InterruptedException {
        return nodeVisitor(node, isDirectDependency, new ArrayList<NodeResult>());
    }

    /**
     * Recursively visits each node in the dependency tree, fetching CVE information for each dependency and its children. This method is called for each node in the tree, starting with the root nodes (direct dependencies) and then visiting their children (transitive dependencies).
     * @param node
     * @param isDirectDependency
     * @param results A list to accumulate the results as the tree is traversed. This allows us to return a single list of NodeResult objects at the end of the traversal.
     * @return A list of NodeResult objects containing information about each dependency, including whether it is a direct dependency, whether it has associated CVEs, the severity of those CVEs, and any fixed versions available.
     * @throws IOException
     * @throws InterruptedException
     */
    private List<NodeResult> nodeVisitor(DependencyNode node, boolean isDirectDependency, List<NodeResult> results)
            throws IOException, InterruptedException {
        CveService cveService = new CveService();

        if (visitedDependencies.contains(node.getArtifact().getArtifactId() + ":" + node.getArtifact().getVersion())) {
            return results;
        }
        totalDependencies++;
        visitedDependencies.add(node.getArtifact().getArtifactId() + ":" + node.getArtifact().getVersion());


        List<CveInfo> cveInfos = cveService.fetchCves(new DependencyModel(node.getArtifact().getArtifactId(),
                node.getArtifact().getGroupId(), node.getArtifact().getVersion()));

        boolean hasCve = !cveInfos.isEmpty();
        String fixedVersion = fixedVersion(cveInfos);
        double severity = getMaxSeverity(cveInfos);

        NodeResult result = new NodeResult(node.getArtifact().getArtifactId() + ":" + node.getArtifact().getVersion(),
                isDirectDependency, hasCve, severity, fixedVersion);
        results.add(result);

        for (DependencyNode child : node.getChildren()) {
            // Visit children of node
            results.addAll(nodeVisitor(child, false, results));
        }

        fetchedDependencies++;
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

    /**
     * Returns the maximum CVSS score from the list of CVE information. This represents the severity of the most severe vulnerability associated with the dependency.
     */
    private double getMaxSeverity(List<CveInfo> cveInfos) {
        double maxSeverity = 0.0;
        for (CveInfo info : cveInfos) {
            if (info.cvsScore() > maxSeverity) {
                maxSeverity = info.cvsScore();
            }
        }
        return maxSeverity;
    }

    /**
     * Computes the dependency metric result. This method is run in a background thread to allow for status updates on the main thread.
     * @param ctx The metric context containing information about the project and environment.
     * @return The computed dependency result.
     */
    private DependencyResult computeResult(MetricContext ctx) {
        bootstrapping = true;
        // Bootstrap the repo
        RepositorySystem repositorySystem = MavenBootstrap.newRepositorySystem();
        RepositorySystemSession repositorySystemSession = MavenBootstrap.newSession(repositorySystem);

        resolving = true;
        // Resolve the dependency tree
        resolver = new DependencyTreeResolver(repositorySystem, repositorySystemSession);
        Path projectPath = ctx.getProjectPath();

        Path pomPath = projectPath.resolve("pom.xml");
        if (!pomPath.toFile().exists()) {
            Console.error("Project did not contain a pom.xml file");
            return null;
        }

        List<DependencyTree> trees = null;
        try {
            trees = resolver.resolvePom(pomPath.toString());
        } catch (Exception e) {
            Console.error("Project did not contain a pom.xml file or it could not be resolved");
            return null;
        }

        fetchingCves = true;

        // Fetch CVE information for dependencies
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

    /**
     * Returns the current status of the dependency metric computation for display in the TUI. This is used to provide feedback to the user about what stage of the computation is currently happening.
     * @return The current status message.
     */
    private String getStatus() {
        if (fetchingCves)       return "Fetching CVE information for dependencies" + (fetchedDependencies > 0 ? " (" + fetchedDependencies + "/" + totalDependencies + ")" : "") + "...";
        if (resolving)          return "Resolving dependency tree" + (resolver != null ? " (" + resolver.resolvedModules + "/" + resolver.totalModules + ")" : "") + "...";
        if (bootstrapping)      return "Bootstrapping maven...";
        
        return "Starting...";
    }
}