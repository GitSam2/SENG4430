import junit.framework.AssertionFailedError;
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
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelfTest {
    @Test
    public void testSelf() throws IOException, AssertionFailedError {
        Path projectPath = Path.of("../SENG4430/tree/main/src/main/java/org/example");
        ProjectParser parser = new ProjectParser();
        MetricContext ctx = new MetricContext(projectPath, parser.parseProject(projectPath));
        // CC test
        CyclomaticComplexityMetric ccMetric = new CyclomaticComplexityMetric();
        CyclomaticComplexityResult ccResult = ccMetric.compute(ctx);
        System.out.println(ccResult.output());
        assertEquals(21, ccResult.getHighestCCScoredFile().getHighestScore() );

        // Dependency test


        // DIT test
        double dit = 0;
        DitMetric ditMetric = new DitMetric();
        DitResult ditResult = ditMetric.compute(ctx);
        dit = ditResult.getMeanDIT();
        System.out.println(ditResult.output());
        assertEquals(1, dit);

        // Id length test
        double maxIdentifierLength = 0;
        IdLengthMetric idMetric = new IdLengthMetric();
        IdLengthResult idResult = idMetric.compute(ctx);
        maxIdentifierLength = idResult.getMaxIdentifierLength();
        System.out.println(idResult.output());
        assertEquals(1, maxIdentifierLength);

        // Nested-depth
        double flaggedCount = 0;
        LoopDepthMetric NDMetric = new LoopDepthMetric();
        LoopMetrics NDResult = NDMetric.compute(ctx);
        flaggedCount = NDResult.flaggedCount;
        System.out.println("Flagged count: " + flaggedCount);
        assertEquals(1.0, flaggedCount);

        // WMC test
        WmcMetric wmcMetric = new WmcMetric();
        WmcResult wmcResult = wmcMetric.compute(ctx);
        System.out.println(wmcResult.output());
        assertTrue(wmcResult.getMeanWMC() >= 0);
    }
}
