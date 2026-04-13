package ccTest;

import org.example.services.CyclomaticComplexity.CyclomaticComplexityMetric;
import org.example.services.CyclomaticComplexity.CyclomaticComplexityResult;
import org.example.services.MetricContext;
import org.example.services.ProjectParser;
import org.junit.Test;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CCTest {
    @Test
    public void testCCMain() throws IOException {
        Path projectPath = Path.of("src/main/resources/powsybl-open-loadflow/src");
        ProjectParser parser = new ProjectParser();
        MetricContext ctx = new MetricContext(projectPath, parser.parseProject(projectPath));
        CyclomaticComplexityMetric metric = new CyclomaticComplexityMetric();
        CyclomaticComplexityResult result = metric.compute(ctx);
        System.out.println(result.output());
        assertEquals(99, result.getHighestCCScoredFile().getHighestScore());
    }

    @Test
    public void testCCMultiClass() throws IOException {
        Path projectPath = Path.of("src/test/java/ccTest/multiClassExample");
        ProjectParser parser = new ProjectParser();
        MetricContext ctx = new MetricContext(projectPath, parser.parseProject(projectPath));
        CyclomaticComplexityMetric metric = new CyclomaticComplexityMetric();
        CyclomaticComplexityResult result = metric.compute(ctx);
        System.out.println(result.output());
        assertEquals(0, result.getHighestCCScoredFile().getHighestScore());
    }

    @Test
    public void testCCSingleclass() throws IOException, AssertionFailedError {
        Path projectPath = Path.of("src/test/java/ccTest/singleClassExample");
        ProjectParser parser = new ProjectParser();
        MetricContext ctx = new MetricContext(projectPath, parser.parseProject(projectPath));
        CyclomaticComplexityMetric metric = new CyclomaticComplexityMetric();
        CyclomaticComplexityResult result = metric.compute(ctx);
        System.out.println(result.output());
        assertEquals(0, result.getHighestCCScoredFile().getHighestScore() );
    }

    @Test
    public void testCCSingleton() throws IOException, AssertionFailedError {
        Path projectPath = Path.of("src/test/java/ccTest/singleton");
        ProjectParser parser = new ProjectParser();
        MetricContext ctx = new MetricContext(projectPath, parser.parseProject(projectPath));
        CyclomaticComplexityMetric metric = new CyclomaticComplexityMetric();
        CyclomaticComplexityResult result = metric.compute(ctx);
        System.out.println(result.output());
        assertEquals(1, result.getHighestCCScoredFile().getHighestScore() );
    }

    @Test
    public void testCCStatic() throws IOException, AssertionFailedError {
        Path projectPath = Path.of("src/test/java/ccTest/staticClass");
        ProjectParser parser = new ProjectParser();
        MetricContext ctx = new MetricContext(projectPath, parser.parseProject(projectPath));
        CyclomaticComplexityMetric metric = new CyclomaticComplexityMetric();
        CyclomaticComplexityResult result = metric.compute(ctx);
        System.out.println(result.output());
        assertEquals(0, result.getHighestCCScoredFile().getHighestScore() );
    }
}
