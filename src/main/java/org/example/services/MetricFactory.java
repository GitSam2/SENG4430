package org.example.services;

import java.util.List;
import java.util.concurrent.Callable;

import org.example.picocli.MetricCommand;

/*
 * Factory class for creating Metric instances paired with CLI commands.
 * to add a new metric, add a single entry to the list below.
 * metrics with unique CLI options should use a dedicated command class instead of MetricCommand.
 */
public class MetricFactory {

    public record MetricEntry(String name, Metric<?> metric, Callable<Integer> command) {
    }

    public static List<MetricEntry> all() {
        return List.of(
                new MetricEntry("dit", new org.example.services.dit.DitMetric(), new MetricCommand("dit")),
                new MetricEntry("cc", new org.example.services.CyclomaticComplexity.CyclomaticComplexityMetric(),
                        new MetricCommand("cc")),
                new MetricEntry("dep", new org.example.services.dependencies.DependencyMetric(),
                        new MetricCommand("dep")),
                new MetricEntry("id", new org.example.services.IdLength.IdLengthMetric(), new MetricCommand("id")),
                new MetricEntry("nested-depth", new org.example.services.NestedDepth.LoopDepthMetric(),
                        new MetricCommand("nested-depth")),
                new MetricEntry("wmc", new org.example.services.wmc.WmcMetric(), new MetricCommand("wmc")));
    }

    /**
     * Find the metric by name or return null if not found
     * 
     * @param metricName the id of the metric to find
     * @return the metric instance or null if not found
     */
    public static Metric<?> getMetric(String metricName) {
        List<MetricEntry> entries = all();
        for (var entry : entries) {
            if (entry.name().equals(metricName)) {
                return entry.metric();
            }
        }
        return null;
    }
}
