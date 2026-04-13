package org.example.services.dit;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.picocli.Console;
import org.example.services.Result;

/*
 * This class is uses for holding the result of the DIT metric
 * You can use it by calling its public methods to get results
 * e.g. result.getDIT() to get the DIT
 */
public class DitResult implements Result {
    // class attributes
    int maxDIT;
    double meanDIT;
    int noWarnThresholds;
    int noFailThresholds;
    boolean result;
    int noAnalysedClasses;
    Map<String, Integer> classes; // class name, DIT
    int warnThreshold;
    int failThreshold;

    // constructor
    public DitResult(Map<String, Integer> classes, int warnThreshold, int failThreshold) {
        this.classes = classes;
        this.warnThreshold = warnThreshold;
        this.failThreshold = failThreshold;
        recomputeSummary(warnThreshold, failThreshold);
    }

    @Override
    public String output() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Classes analysed : %,d%n", noAnalysedClasses));
        sb.append(String.format("Max DIT          : %d%n", maxDIT));
        sb.append(String.format("Mean DIT         : %.1f%n", meanDIT));
        sb.append(String.format("Thresholds       : warn ≥ %d   fail ≥ %d%n", warnThreshold, failThreshold));
        sb.append("Result           : ");
        sb.append(result
                ? Console.boldGreen("PASS")
                : Console.boldRed(String.format("FAIL (%d classes ≥ %d)", noFailThresholds, failThreshold)));
        sb.append("%n".formatted());

        sb.append("-".repeat(Console.terminalWidth()));
        sb.append(System.lineSeparator());

        List<Map.Entry<String, Integer>> sorted = classes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toList());

        String[] headers = { "Rank", "DIT", "Class", "Status" };
        int[] widths = { 6, 5, Console.terminalWidth() - 26, 15 };

        String[][] rows = new String[sorted.size()][headers.length];
        for (int i = 0; i < sorted.size(); i++) {
            Map.Entry<String, Integer> entry = sorted.get(i);
            int dit = entry.getValue();
            rows[i][0] = String.valueOf(i + 1);
            rows[i][1] = String.valueOf(dit);
            rows[i][2] = entry.getKey();
            rows[i][3] = dit >= failThreshold ? Console.boldRed("exceeds fail")
                    : dit >= warnThreshold ? Console.yellow("exceeds warn")
                            : Console.boldGreen("OK");
        }

        sb.append(Console.table(headers, widths, rows));
        return sb.toString();
    }

    // getters
    public int getMaxDIT() {
        return maxDIT;
    }

    public double getMeanDIT() {
        return meanDIT;
    }

    public int getNoWarnThresholds() {
        return noWarnThresholds;
    }

    public int getNoFailThresholds() {
        return noFailThresholds;
    }

    public boolean getResult() {
        return result;
    }

    public int getNoAnalysedClasses() {
        return noAnalysedClasses;
    }

    public Map<String, Integer> getClasses() {
        return classes;
    }

    // helper functions
    private void recomputeSummary(int warnThreshold, int failThreshold) {
        // early catch for unnecessary calculations
        if (classes.isEmpty()) {
            this.maxDIT = 0;
            this.meanDIT = 0;
            return;
        }

        int sum = 0;
        int max = 0;
        int warns = 0;
        int fails = 0;

        int classSize = classes.size();

        for (int classDit : classes.values()) {
            sum += classDit;
            if (classDit > max)
                max = classDit; // get the max DIT
            if (classDit >= failThreshold)
                fails++; // get the number of fails
            else if (classDit >= warnThreshold)
                warns++; // get the number of warns
        }
        this.maxDIT = max;
        this.meanDIT = (double) sum / classSize; // calculate the average
        this.noWarnThresholds = warns;
        this.noFailThresholds = fails;
        this.result = fails == 0;
        this.noAnalysedClasses = classSize;
    }

}