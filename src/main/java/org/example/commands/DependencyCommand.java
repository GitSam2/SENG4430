package org.example.commands;

import org.example.QualityToolCLI;
import org.example.utils.Console;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "dep",
        aliases = {},
        mixinStandardHelpOptions = true,
        description = {
                "Dependency Metric"
        }
)
public class DependencyCommand extends BaseMetricCommand {
    @ParentCommand
    QualityToolCLI parent;

    @Option(names = {"-r", "--print"},
            description = "Is printed")
    String printed;

    @Override
    public String displayName() {
        return "Dependency Analysis";
    }

    @Override
    public Integer execute() throws Exception {
        System.out.println(Console.header("DEPENDENCY %s", printed));

        return 0;
    }

    @Override
    public boolean exceededThreshold(Object result) {
        return false;
    }
}
