package org.example.commands;

import org.example.QualityToolCLI;
import picocli.CommandLine.*;

@Command(
        name = "one",
        aliases = {},
        mixinStandardHelpOptions = true,
        description = {
                "Test command one"
        }
)
public class TestCommandOne extends BaseMetricCommand {
    @ParentCommand
    QualityToolCLI parent;

    @Override
    public String displayName() {
        return "one";
    }

    @Override
    public Integer execute() throws Exception {
        System.out.printf("TestCommandOne.call(): %s%n", parent.projectPath.toString());

        return 0;
    }

    @Override
    public boolean exceededThreshold(Object result) {
        return false;
    }
}
