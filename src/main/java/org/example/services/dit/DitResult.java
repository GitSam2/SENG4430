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
    int meanDIT;
    int noWarnThresholds;
    int noFailThresholds;
    boolean result;
    Map<String, Integer> classes;

    // constructors
    public DitResult(Map<String, Integer> classes) {
        this.classes = classes;
    }

    public int getDIT() {
        return 0;
    }
}
