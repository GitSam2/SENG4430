/** Title: IdLengthResult.java
*   @author Troy Madden
*   Created: 18th February, 2026
*   @version 1.4
*   Description: Created for Assignment 1 SENG4430. Group Assignment testing the software quality for a 
*   power plant. This class takes the added up identifiers from IdLengthMetric.java and prints them to the console.
*/

package org.example.services.IdLength;

import org.example.services.Result;

public class IdLengthResult implements Result {

    int totalIdentifiers; // total number of identifiers being analysed
    int maxIdentifierLength; // the longest identifiers length
    int totalLength; // total length of all identifiers combined
    int exceedsMaxLength; // how many identifiers exceed 30 characters

    @Override
    public String output() {
        return buildAnalysisOutput();
    }

    // constructor
    public IdLengthResult(int totalIdentifiers, int maxIdentifierLength, int totalLength, int exceedsMaxLength) {
        this.totalIdentifiers = totalIdentifiers;
        this.maxIdentifierLength = maxIdentifierLength;
        this.totalLength = totalLength;
        this.exceedsMaxLength = exceedsMaxLength;
    }

    // text block with outputs
    public String buildAnalysisOutput() {

    double average = totalIdentifiers == 0
            ? 0
            : (double) totalLength / totalIdentifiers;

    return """
            -------------------------------------------
            Identifier Length Analysis
            -------------------------------------------
            Total identifiers: %d
            Maximum identifier length: %d
            Average identifier length: %.2f
            Identifiers exceeding 30 characters: %d
            -------------------------------------------
            """.formatted(totalIdentifiers, maxIdentifierLength, average, exceedsMaxLength);
    }

    // getter for maxIdentifierLength to be used in testing
    public int getMaxIdentifierLength() { 
        return maxIdentifierLength;
    }

}
