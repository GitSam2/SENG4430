package org.example;

import org.example.picocli.ParentCommand;
import org.example.services.MetricContext;
import org.example.services.MetricEntry;
import org.example.services.MetricFactory;

import picocli.CommandLine;

public class QualityToolApp {
    public static MetricContext ctx;

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new ParentCommand());
        for (MetricEntry entry : MetricFactory.all()) {
            cmd.addSubcommand(entry.name(), entry.command());
        }
        System.exit(cmd.setExecutionStrategy(new CommandLine.RunAll()).execute(args));
    }
}
