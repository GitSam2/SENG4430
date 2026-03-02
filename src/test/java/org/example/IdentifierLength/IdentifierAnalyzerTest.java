package org.example.IdentifierLength;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class IdentifierAnalyzerTest {

    @TempDir
    Path tempDir;

    @Test
    void totalIdentifiersStartsAtZero() {
        IdentifierAnalyzer analyzer = new IdentifierAnalyzer();
        assertEquals(0, analyzer.getTotalIdentifiers()); // Checks totalIdentifiers
    }

    @Test
    void maxIdentifierLengthStartsAtZero() {
        IdentifierAnalyzer analyzer = new IdentifierAnalyzer();
        assertEquals(0, analyzer.getMaxIdentifierLength()); // Checks maxIdentifierLength
    }

    @Test
    void totalLengthStartsAtZero() {
        IdentifierAnalyzer analyzer = new IdentifierAnalyzer();
        assertEquals(0, analyzer.getTotalLength()); // Checks totalLength
    }
}