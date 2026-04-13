package org.example.services.CyclomaticComplexity;

import org.example.services.Result;
import org.example.picocli.Console;

import java.util.List;

public class CyclomaticComplexityResult implements Result {

    private final List<CCContext> result;
    private boolean exceededThresholdResult = false;

    private CCContext highestCCScoredFile;
    private Double averageCCScore;

    public CyclomaticComplexityResult(List<CCContext> result) {
        this.result = result;
        ResultAnalysis(result);
    }

    @Override
    public String output() {
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
        String[][] rows = new String[result.size()][headers.length];
        int rowIndex = 0;
        for (CCContext cyclomaticComplexityResult : result) {
            rows[rowIndex][0] = cyclomaticComplexityResult.getFileName();
            rows[rowIndex][1] = "%.1f".formatted(cyclomaticComplexityResult.getAverageScore());
            rows[rowIndex][2] = String.valueOf(cyclomaticComplexityResult.getHighestScore());

            switch(cyclomaticComplexityResult.getSeverity()) {
                case INFO -> rows[rowIndex][3] = Console.cyan("INFO");
                case WARNING -> rows[rowIndex][3] = Console.yellow("WARNING");
                case ERROR -> {
                    rows[rowIndex][3] = Console.boldRed("ERROR");
                    exceededThresholdResult = true;
                }
            }

            rowIndex++;
        }
        String metricDescription = "Cyclomatic Complexity Metric \nSeverity guide: \nINFO: score <= 10 -> Acceptable \nWARNING: 15 >= score > 10 \nERROR: score < 15\n";
        String projectAnalysis = "\nProject Analysis: \nTotal file: "+ result.size() + "\nHighest Score: " + highestCCScoredFile.getHighestScore() + "\nAverage Score: " + getAverageCCScore()+ "\n";
        return metricDescription + Console.table(headers, widths, rows) + projectAnalysis;
    }

    public void ResultAnalysis(List<CCContext> result) {
        averageCCScore = 0.0;
        highestCCScoredFile = result.getFirst();
        if (highestCCScoredFile ==null) {
            System.out.println("No file found, please try running the command again");
            return;
        }
        for (CCContext file : result) {
            assert highestCCScoredFile != null;
            if (file.getHighestScore() > highestCCScoredFile.getHighestScore()) {
                highestCCScoredFile = file;
            }
            if (!file.getAverageScore().isNaN()) {
                averageCCScore +=file.getAverageScore();
            }
        }
        averageCCScore = averageCCScore / result.size();
    }

    public Double getAverageCCScore() {
        return averageCCScore;
    }

    public CCContext getHighestCCScoredFile() {
        return highestCCScoredFile;
    }

    public boolean isExceededThresholdResult() {
        return exceededThresholdResult;
    }
}
