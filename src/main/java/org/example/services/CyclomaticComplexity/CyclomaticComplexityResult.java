package org.example.services.CyclomaticComplexity;

import java.util.List;

import org.example.picocli.Console;
import org.example.services.Result;

public class CyclomaticComplexityResult implements Result {
    private List<CyclomaticComplexityFileResult> fileResults;

    // constructor
    public CyclomaticComplexityResult(List<CyclomaticComplexityFileResult> fileResults) {
        this.fileResults = fileResults;
    }

    @Override
    public String output() {
        // Headers of the table columns
        String[] headers = {
                "File",
                "Average",
                "Highest Score",
                "Severity"
        };

        // Widths in characters of each column
        // Currently terminal width with columns is determined by hand
        int[] widths = {
                Console.terminalWidth() - 31,
                8,
                14,
                9
        };

        // Create a row X column table of cells below the header row
        String[][] rows = new String[fileResults.size()][headers.length];
        int rowIndex = 0;
        for (CyclomaticComplexityFileResult cyclomaticComplexityFileResult : fileResults) {
            rows[rowIndex][0] = cyclomaticComplexityFileResult.getFileName();
            rows[rowIndex][1] = "%.1f".formatted(cyclomaticComplexityFileResult.getAverageScore());
            rows[rowIndex][2] = String.valueOf(cyclomaticComplexityFileResult.getHighestScore());

            switch (cyclomaticComplexityFileResult.getSeverity()) {
                case INFO -> rows[rowIndex][3] = Console.cyan("INFO");
                case WARNING -> rows[rowIndex][3] = Console.yellow("WARNING");
                case ERROR -> {
                    rows[rowIndex][3] = Console.boldRed("ERROR");
                }
            }

            rowIndex++;
        }

        return (Console.table(headers, widths, rows));
    }

}