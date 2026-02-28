package org.example.services.dit;

import java.util.Map;

/*
 * This class is uses for holding the result of the DIT metric
 * You can use it by calling its public methods to get results
 * e.g. result.getDIT() to get the DIT
 */
public class DitResult {
    // class attributes
    int noAnalysedClasses;
    int noExcludedClasses;
    int maxDIT;
    double meanDIT;
    int noWarnThresholds;
    int noFailThresholds;
    boolean result;
    Map<String, Integer> classes; // class name, DIT

    // constructors
    public DitResult(Map<String, Integer> classes) {
        this.classes = classes;
        recomputeSummary();
    }

    private void recomputeSummary() {
        // early catch for unnecessary calculations
        if (classes.isEmpty()) {
            this.maxDIT = 0;
            this.meanDIT = 0;
            return;
        }

        double sum = 0;
        int max = Integer.MIN_VALUE;
        for (int classDit : classes.values()) {
            sum += classDit;
            if (classDit > max) max = classDit; // get the max DIT
        }
        this.maxDIT = max;
        this.meanDIT = sum / classes.size(); // calculate the average
    }

    public double getMeanDIT() {
        return meanDIT;
    }
}
