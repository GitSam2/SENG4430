package ditTest;

import static org.junit.jupiter.api.Assertions.*;

import com.github.javaparser.ast.CompilationUnit;
import org.example.services.ProjectParser;
import org.example.services.dit.DitMetric;
import org.example.services.dit.DitResult;
import org.example.services.MetricContext;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

class DitMetricTest {

    @Test
    void singleClassExample() {
        Path testInputPath = Path.of("src/test/java/ditTest/singleClassExample");

        ProjectParser parser = new ProjectParser();
        double dit = 0;

        try {
            MetricContext ctx = new MetricContext(parser.parseProject(testInputPath));
            DitMetric metric = new DitMetric();
            DitResult result = metric.compute(ctx);
            dit = result.getMeanDIT();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1, dit);
    }

    @Test
    void multiClassExample() {
        Path testInputPath = Path.of("src/test/java/ditTest/multiClassExample");

        ProjectParser parser = new ProjectParser();
        double dit = 0;

        try {
            MetricContext ctx = new MetricContext(parser.parseProject(testInputPath));
            DitMetric metric = new DitMetric();
            DitResult result = metric.compute(ctx);
            dit = result.getMeanDIT();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Pet (DIT: 1) <- Dog (DIT: 2), Cat (DIT: 2)
        // Main (DIT: 1)
        // Average = (1+1+2+2)/4 = 1.5
        assertEquals(1.5, dit);// should be
    }


}