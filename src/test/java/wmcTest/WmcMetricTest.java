package wmcTest;

import org.example.services.ProjectParser;
import org.example.services.MetricContext;
import org.example.services.wmc.WmcMetric;
import org.example.services.wmc.WmcResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WmcMetricTest {

    @Test
    void simpleExample() {

        Path testInputPath = Path.of("src/test/java/wmcTest");

        ProjectParser parser = new ProjectParser();

        try {

            MetricContext ctx =
                    new MetricContext(parser.parseProject(testInputPath));

            WmcMetric metric = new WmcMetric();

            WmcResult result = metric.compute(ctx);

            System.out.println(result.output());

            assertTrue(result.getMeanWMC() >= 0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
