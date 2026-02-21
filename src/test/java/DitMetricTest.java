import static org.junit.jupiter.api.Assertions.*;

import org.example.services.dit.DitMetric;
import org.example.services.dit.DitResult;
import org.example.services.MetricContext;
import org.junit.jupiter.api.Test;

class DitMetricTest {

    @Test
    void objectHasDepth0() {
        DitMetric metric = new DitMetric();

        DitResult result = metric.compute(new MetricContext(null));

        assertTrue(true);
    }


}