/** Title: IdentifierReport.java
*   @author Troy Madden
*   Created: 18th February, 2026
*   @version 1.3
*   Description: Created for Assignment 1 SENG4430. Group Assignment testing the software quality for a power plant. This class imports the
*   rules and uses them to create reports and output for the user.
*/

package org.example.IdentifierLength;

public class IdentifierReport {

    public String buildAnalysisOutput(IdentifierAnalyzer analyzer) { // Takes the statistics from the analyzer and creates a report
        return "Identifier Length Analysis\n"
                + "---------------------------\n"
                + "Total identifiers: " + analyzer.getTotalIdentifiers() + "\n"
                + "Maximum identifier length: " + analyzer.getMaxIdentifierLength() + "\n"
                + "Average identifier length: "
                + String.format("%.2f", analyzer.getAverageIdentifierLength());
    }

    public String buildThresholdOutput(IdentifierThreshold threshold) { // Takes the rules from the threshold and creates a report
        return "Identifier Length Threshold\n"
                + "----------------------------\n"
                + "Maximum allowed length: " + threshold.getMaxAllowedLength() + "\n"
                + "Identifiers exceeding limit: " + threshold.getExceededCount();
    }
}