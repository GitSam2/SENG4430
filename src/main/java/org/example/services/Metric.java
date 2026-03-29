package org.example.services;

public interface Metric<T extends Result> {
    String id();

    T compute(MetricContext ctx);
}