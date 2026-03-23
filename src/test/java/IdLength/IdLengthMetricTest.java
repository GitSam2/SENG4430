/** Title: IdLengthMetricTest.java
*   @author Troy Madden
*   Created: 18th February, 2026
*   @version 1.4
*   Description: Created for Assignment 1 SENG4430. Group Assignment testing the software quality for a 
*   power plant. This class allows testing of boundaries and thresholds. Helps make sure the data being
*   outputted has correct calculations.
*/

package IdLength;

import java.io.IOException;
import java.nio.file.Path;

import org.example.services.IdLength.IdLengthMetric;
import org.example.services.IdLength.IdLengthResult;
import org.example.services.MetricContext;
import org.example.services.ProjectParser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class IdLengthMetricTest {
    @Test
    void singleClassExample() {
        Path testInputPath = Path.of("src/test/java/IdLength/singleClassExample"); // change path of analysis to the singleclassexample

        ProjectParser parser = new ProjectParser();
        double maxIdentifierLength = 0;

        try {
            MetricContext ctx = new MetricContext(testInputPath, parser.parseProject(testInputPath));
            IdLengthMetric metric = new IdLengthMetric();
            IdLengthResult result = metric.compute(ctx);
            maxIdentifierLength = result.getMaxIdentifierLength();
            System.out.println(result.output());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(4, maxIdentifierLength); // change number to the actual max length in singleclassexample
    }

    @Test
    void multiClassExample() {
        Path testInputPath = Path.of("src/test/java/IdLength/multiClassExample"); // change path of analysis to the multiclassexample

        ProjectParser parser = new ProjectParser();
        double maxIdentifierLength = 0;

        try {
            MetricContext ctx = new MetricContext(testInputPath, parser.parseProject(testInputPath));
            IdLengthMetric metric = new IdLengthMetric();
            IdLengthResult result = metric.compute(ctx);
            maxIdentifierLength = result.getMaxIdentifierLength();
            System.out.println(result.output());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(36, maxIdentifierLength); // change number to the actual max length in multiclassexample
    }
}
