package org.example.commands;

import org.example.QualityToolCLI;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "two",
        aliases = {},
        mixinStandardHelpOptions = true,
        description = {
                "Test command two"
        }
)
public class TestCommandTwo extends BaseMetricCommand {
    @ParentCommand
    QualityToolCLI command;

    @Override
    public String displayName() {
        return "two";
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("TestCommandTwo.call(): %s".formatted(command.projectPath.toString()));
        return 0;
    }

    @Override
    public Object execute() throws Exception {
        return null;
    }

    @Override
    public boolean exceededThreshold(Object result) {
        return false;
    }
}
