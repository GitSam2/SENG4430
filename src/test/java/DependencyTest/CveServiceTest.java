package DependencyTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.example.services.dependencies.CveInfo;
import org.example.services.dependencies.CveService;
import org.example.services.dependencies.DependencyModel;
import org.junit.jupiter.api.Test;

public class CveServiceTest {
    @Test
    public void testGetCveInfoBatch() {
        // Given
        CveService cveService = new CveService();
        DependencyModel model = new DependencyModel("zookeeper", "org.apache.zookeeper", "3.9.0");

        // When
        List<CveInfo> cveInfos = null;
        try {
            cveInfos = cveService.fetchCves(model);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            assertTrue(false, "CVE fetch threw an exception: " + e.getMessage());
        }

        // Then
        assertNotNull(cveInfos);
        assertFalse(cveInfos.isEmpty());
        assertEquals("GHSA-2hmj-97jw-28jh", cveInfos.get(0).id());
        assertEquals("Apache ZooKeeper: Insufficient Permission Check in AdminServer Snapshot/Restore Commands", cveInfos.get(0).summary());
    }
}
