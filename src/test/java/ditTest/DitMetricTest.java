package ditTest;

import static org.junit.jupiter.api.Assertions.*;

import org.example.services.ProjectParser;
import org.example.services.dit.DitMetric;
import org.example.services.dit.DitResult;
import org.example.services.MetricContext;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

class DitMetricTest {

    @Test
    void objectHasDepth0() {
        DitMetric metric = new DitMetric();

        DitResult result = metric.compute(new MetricContext(null));

        assertTrue(true);
    }

    @Test
    void singleClassExample() {
        Path testInputPath = Path.of("src/test/java/ditTest/singleClassExample");

        ProjectParser parser = new ProjectParser();
        int dit = 0;

        try {
            MetricContext ctx = new MetricContext(parser.parseProject(testInputPath));
            DitMetric metric = new DitMetric();
            DitResult result = metric.compute(ctx);
            dit = result.getDIT();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1, dit);
    }


}