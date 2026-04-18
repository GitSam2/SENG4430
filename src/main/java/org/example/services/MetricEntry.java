package org.example.services;

import java.util.concurrent.Callable;

public record MetricEntry(String name, Metric<?> metric, Callable<Integer> command) {

}
