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
                + "Total identifiers: " + totalIdentifiers + "\n"
                + "Maximum identifier length: " + maxIdentifierLength + "\n"
                + "Average identifier length: "
                + String.format("%.2f", totalLength);
    }

    public int getMaxIdentifierLength() {
        return maxIdentifierLength;
    }

}
