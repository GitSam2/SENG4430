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

    // test if an identifier is over 30 is it added to over max length counter

    // test if i only have 1 identifier is total indentifiers ONE
}