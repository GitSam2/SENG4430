package org.example.services;

public interface Metric<T> {
    String id();
    T compute(MetricContext ctx);
}