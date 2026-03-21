package org.example.commands;

import org.example.QualityToolCLI;
import org.example.utils.Console;
import picocli.CommandLine.*;
import picocli.CommandLine.Model.CommandSpec;

import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command
public abstract class BaseMetricCommand implements Callable<Integer>, MetricCommand {
    @ParentCommand
    protected QualityToolCLI parent;

    protected Path projectPath()        { return parent.projectPath; }
    protected boolean failOnThreshold() { return parent.failThreshold; }

    @Spec CommandSpec spec;

    @Override
    public Integer call() throws Exception {
        Console.init(spec, !parent.noColor);
        System.out.println(Console.header("Running: %s", displayName()));

        System.out.println(Console.bold(Console.yellow("▶ " + displayName())));
        System.out.println(Console.divider('─'));

        Object result = execute();

        if (parent.failThreshold && exceededThreshold(result)) {
            Console.error("Threshold exceeded — failing build.");
            return 1;
        }

        System.out.println();
        Console.success("Done %s".formatted(displayName()));
        System.out.println();
        return 0;
    }
}
