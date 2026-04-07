package org.example.services;

/*
    * Factory class for creating Metric instances.
*/
public class MetricFactory {
    public static Metric<?> getMetric(String metricName) {
        return switch (metricName.toLowerCase()) {
            case "dit" -> new org.example.services.dit.DitMetric();
            case "cc" -> new org.example.services.CyclomaticComplexity.CyclomaticComplexityMetric();
            case "dep" -> new org.example.services.NestedDepth.LoopDepthMetric();
            case "id" -> new org.example.services.IdLength.IdLengthMetric();
            case "nested-depth" -> new org.example.services.NestedDepth.LoopDepthMetric();
            case "wmc" -> new org.example.services.wmc.WmcMetric();
            default -> null;
        };
    }
}
