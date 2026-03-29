package org.example;

import java.util.List;

import org.example.picocli.MetricCommand;
import org.example.picocli.ParentCommand;
import org.example.services.MetricContext;

import picocli.CommandLine;

public class QualityToolApp {
    public static MetricContext ctx;

    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new ParentCommand());
        for (String name : List.of("dit", "cc", "dep", "id", "nested-depth", "wmc")) {
            cmd.addSubcommand(name, new MetricCommand(name));
        }
        System.exit(cmd.setExecutionStrategy(new CommandLine.RunAll()).execute(args));
    }
}
