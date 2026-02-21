package org.example.services.dit;

import org.example.services.Metric;
import org.example.services.MetricContext;

import java.util.List;

/*
 * This class is uses for getting the Depth of Inheritance Tree (DIT)
 * Definition: DIT is the maximum length from the node (class) to the root of the tree.
 */
public final class DitMetric implements Metric<DitResult> {
    // class attributes
    int noAnalysedClasses;
    int noExcludedClasses;
    int maxDIT;
    int meanDIT;
    int noWarnThresholds;
    int noFailThresholds;
    boolean result;

    List<DitResult> classResults;

    @Override public String id() { return "DIT"; }

    @Override
    public DitResult compute(MetricContext ctx) {
        return null;
    }
}
