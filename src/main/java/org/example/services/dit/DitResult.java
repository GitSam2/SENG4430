package org.example.services.dit;
import org.example.services.Result;
import java.util.Map;

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

    // constructor
    public DitResult(Map<String, Integer> classes, int warnThreshold, int failThreshold) {
        this.classes = classes;
        recomputeSummary(warnThreshold, failThreshold);
    }

    @Override
    public String output() {
        StringBuilder sb = new StringBuilder();

        sb.append("Static Metrics: DIT\n");
        sb.append("────────────────────────────────────────────────────────\n");

        sb.append(String.format(
                "Analyzed: %,d classes \n",
                noAnalysedClasses
        ));

        sb.append(String.format(
                "Max DIT: %d               Mean: %.1f \n",
                maxDIT,
                (double) meanDIT
        ));

        sb.append(String.format(
                "Threshold alerts: warns≥%d fails≥%d%n",
                noWarnThresholds,
                noFailThresholds
        ));

        if (result) {
            sb.append("Result: PASS\n");
        } else {
            sb.append(String.format(
                    "Result: FAIL (%d classes ≥ 7)\n",
                    noFailThresholds
            ));
        }

        return sb.toString();
    }

    // getters
    public int getMaxDIT() { return maxDIT; }
    public double getMeanDIT() { return meanDIT; }
    public int getNoWarnThresholds() { return noWarnThresholds; }
    public int getNoFailThresholds() { return noFailThresholds; }
    public boolean getResult() { return result; }
    public int getNoAnalysedClasses() { return noAnalysedClasses; }
    public Map<String, Integer> getClasses() { return classes; }


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
            if (classDit > max) max = classDit; // get the max DIT
            if (classDit >= failThreshold) fails++; // get the number of fails
            else if (classDit >= warnThreshold) warns++; // get the number of warns
        }
        this.maxDIT = max;
        this.meanDIT = (double) sum / classSize; // calculate the average
        this.noWarnThresholds = warns;
        this.noFailThresholds = fails;
        this.result = fails == 0;
        this.noAnalysedClasses = classSize;
    }

    }