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
    void singleClassExample() {
        Path testInputPath = Path.of("src/test/java/ditTest/singleClassExample");

        ProjectParser parser = new ProjectParser();
        double dit = 0;

        try {
            MetricContext ctx = new MetricContext(parser.parseProject(testInputPath));
            DitMetric metric = new DitMetric();
            DitResult result = metric.compute(ctx);
            dit = result.getMeanDIT();
            System.out.println(result.output());
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
            System.out.println(result.output());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Pet (DIT: 1) <- Dog (DIT: 2), Cat (DIT: 2)
        // Main (DIT: 1)
        // Average = (1+1+2+2)/4 = 1.5
        assertEquals(1.5, dit);// should be
    }

    @Test
    void ABCExample() {
        Path testInputPath = Path.of("src/test/java/ditTest/ABCExample");

        ProjectParser parser = new ProjectParser();
        double dit = 0;

        try {
            MetricContext ctx = new MetricContext(parser.parseProject(testInputPath));
            DitMetric metric = new DitMetric();
            DitResult result = metric.compute(ctx);
            dit = result.getMeanDIT();
            System.out.println(result.output());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // A (DIT: 1) <- B (DIT: 2), C (DIT: 2)
        // Average = (1+2+2)/3 = 1.6666666667
        assertEquals(1.6666666667, dit, 0.0000000001);
    }

    @Test
    void ABCDEExample() {
        Path testInputPath = Path.of("src/test/java/ditTest/ABCDEExample");

        ProjectParser parser = new ProjectParser();
        double dit = 0;

        try {
            MetricContext ctx = new MetricContext(parser.parseProject(testInputPath));
            DitMetric metric = new DitMetric();
            DitResult result = metric.compute(ctx);
            dit = result.getMeanDIT();
            System.out.println(result.output());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // A (DIT: 1) -> B (DIT: 2) -> C (DIT: 3) -> D (DIT: 4) -> E (DIT: 5)
        // Average = (1+2+3+4+5)/5 = 3
        assertEquals(3, dit);
    }

    @Test
    void externalLibraryExample() {
        Path testInputPath = Path.of("src/test/java/ditTest/externalLibraryExample");

        ProjectParser parser = new ProjectParser();
        double dit = 0;

        try {
            MetricContext ctx = new MetricContext(parser.parseProject(testInputPath));
            DitMetric metric = new DitMetric();
            DitResult result = metric.compute(ctx);
            dit = result.getMeanDIT();
            System.out.println(result.output());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // CustomNumber extends BigDecimal (external library) (DIT: 1) // since it is outside the project
        // LocalClass (DIT: 1)
        // Average = (1+1)/2 = 1
        assertEquals(1, dit);
    }


}