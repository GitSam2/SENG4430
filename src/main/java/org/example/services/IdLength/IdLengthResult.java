package org.example.services.IdLength;

import org.example.services.Result;

public class IdLengthResult implements Result {

    int totalIdentifiers;
    int maxIdentifierLength;
    int totalLength;

    @Override
    public String output() {
        return buildAnalysisOutput();
    }

    // Constructor
    public IdLengthResult(int totalIdentifiers, int maxIdentifierLength, int totalLength) {
        this.totalIdentifiers = totalIdentifiers;
        this.maxIdentifierLength = maxIdentifierLength;
        this.totalLength = totalLength;
    }

    public String buildAnalysisOutput() {
        return "Identifier Length Analysis\n"
                + "---------------------------\n"
                + "Total identifiers: " + String.valueOf(totalIdentifiers) + "\n"
                + "Maximum identifier length: " + String.valueOf(maxIdentifierLength) + "\n"
                + "Average identifier length: " + String.valueOf(totalLength);
    }

    public int getMaxIdentifierLength() {
        return maxIdentifierLength;
    }

}
