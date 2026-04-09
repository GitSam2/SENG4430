package org.example.picocli;

import java.util.concurrent.Callable;

import org.example.QualityToolApp;
import org.example.services.Metric;
import org.example.services.MetricFactory;
import org.example.services.Result;

import picocli.CommandLine.Command;

/*
    * Each metric is registered as a direct subcommand on the root (e.g. qualitytool cc -p ...)
*/
@Command
public class MetricCommand implements Callable<Integer> {
    private final String metricName;

    public MetricCommand(String metricName) {
        this.metricName = metricName;
    }

    @Override
    public Integer call() {
        // Based on the metricName, we can get the corresponding metric object and
        // execute it
        Metric<?> metric = MetricFactory.getMetric(metricName);
        if (metric == null) {
            Console.error("Unknown metric: " + metricName);
            return 1;
        }

        // start up
        System.out.println(Console.bold(Console.yellow("▶ " + metric.id())));
        System.out.println(Console.divider('─'));

        // Run the metric and display results
        return runMetric(metric);
    }

    private Integer runMetric(Metric<?> metric) {
        // run compute
        Result result = metric.compute(QualityToolApp.ctx);

        // Display results
        if (result == null) {
            Console.error("Metric computation failed for: " + metricName);
            return 1;
        }
        System.out.println(result.output());

        return 0;
    }
}
