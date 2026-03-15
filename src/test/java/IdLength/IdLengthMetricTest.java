package IdLength;

import java.io.IOException;
import java.nio.file.Path;

import org.example.services.MetricContext;
import org.example.services.ProjectParser;
import org.example.services.IdLength.IdLengthMetric;
import org.example.services.IdLength.IdLengthResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class IdLengthMetricTest {
    @Test
    void singleClassExample() {
        Path testInputPath = Path.of("src/test/java/ditTest/singleClassExample");

        ProjectParser parser = new ProjectParser();
        double maxIdentifierLength = 0;

        try {
            MetricContext ctx = new MetricContext(parser.parseProject(testInputPath));
            IdLengthMetric metric = new IdLengthMetric();
            IdLengthResult result = metric.compute(ctx);
            maxIdentifierLength = result.getMaxIdentifierLength();
            System.out.println(result.output());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(4, maxIdentifierLength);
    }

}