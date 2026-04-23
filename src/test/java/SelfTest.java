import org.example.services.CyclomaticComplexity.CyclomaticComplexityMetric;
import org.example.services.CyclomaticComplexity.CyclomaticComplexityResult;
import org.example.services.IdLength.IdLengthMetric;
import org.example.services.IdLength.IdLengthResult;
import org.example.services.MetricContext;
import org.example.services.NestedDepth.LoopDepthMetric;
import org.example.services.NestedDepth.LoopMetrics;
import org.example.services.ProjectParser;
import org.example.services.dit.DitMetric;
import org.example.services.dit.DitResult;
import org.example.services.wmc.WmcMetric;
import org.example.services.wmc.WmcResult;
import org.example.services.dependencies.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SelfTest {
    @Test
    public void testSelf() throws IOException {
        // Dependency test
        // Requires access to pom unlike other metrics, so we have separate metric contexts
        Path projectPath = Path.of(".");
        ProjectParser parser = new ProjectParser();
        MetricContext ctx = new MetricContext(projectPath, parser.parseProject(projectPath));

        DependencyMetric depMetric = new DependencyMetric();
        DependencyResult depResult = depMetric.compute(ctx);
        boolean cveFound = depResult.nodeResults().stream().anyMatch(NodeResult::hasCve);
        depResult.nodeResults().forEach(nodeResult -> {
            System.out.println(nodeResult.id() + " - CVE: " + nodeResult.hasCve() + " - Severity: " + nodeResult.severity());
        });
        assertFalse(cveFound, "Expected no CVEs, but found at least one.");

        projectPath = Path.of("src/main/java");
        parser = new ProjectParser();
        ctx = new MetricContext(projectPath, parser.parseProject(projectPath));
        
        // CC test
        CyclomaticComplexityMetric ccMetric = new CyclomaticComplexityMetric();
        CyclomaticComplexityResult ccResult = ccMetric.compute(ctx);
        System.out.println(ccResult.output());
        assertEquals(21, ccResult.getHighestCCScoredFile().getHighestScore() );

        // DIT test
        double dit = 0;
        DitMetric ditMetric = new DitMetric();
        DitResult ditResult = ditMetric.compute(ctx);
        dit = ditResult.getMeanDIT();
        System.out.println(ditResult.output());
        assertTrue(dit < 2, "Expected mean DIT to be less than 2, but got: " + dit);

        // Id length test
        double maxIdentifierLength = 0;
        IdLengthMetric idMetric = new IdLengthMetric();
        IdLengthResult idResult = idMetric.compute(ctx);
        maxIdentifierLength = idResult.getMaxIdentifierLength();
        System.out.println(idResult.output());
        assertEquals(26, maxIdentifierLength);

        // Nested-depth
        double flaggedCount = 0;
        LoopDepthMetric NDMetric = new LoopDepthMetric();
        LoopMetrics NDResult = NDMetric.compute(ctx);
        flaggedCount = NDResult.flaggedCount;
        System.out.println("Nested Loops That Exceed A Depth Of 3 Are Considered A Code Smell And Are Flagged");
        System.out.println(NDResult.output());
        System.out.println("Flagged count: " + flaggedCount);
        assertEquals(0.0, flaggedCount);

        // WMC test
        WmcMetric wmcMetric = new WmcMetric();
        WmcResult wmcResult = wmcMetric.compute(ctx);
        System.out.println("\n" + wmcResult.output());
        assertTrue(wmcResult.getMeanWMC() >= 0);
    }
}
