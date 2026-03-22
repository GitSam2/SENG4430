package org.example.commands;

import org.example.Main;
import org.example.QualityToolCLI;
import org.example.services.MetricContext;
import org.example.services.dit.DitMetric;
import org.example.services.dit.DitResult;
import org.example.utils.Console;

import picocli.CommandLine.*;



@Command(
        name = "dit",
        aliases = {},
        mixinStandardHelpOptions = true,
        version = "qualitytool 1.0",
        description = {
                "Depth Inheritance Tree Analysis"
        }
)
public class DitCommand extends BaseMetricCommand {
    boolean exceededThresholdResult = false;

    @ParentCommand
    QualityToolCLI parent;

    @Override
    public String displayName() {
        return "Depth Inheritance Tree Analysis";
    }

    @Override
    public Integer execute() throws Exception {
        //============ Parse options ============
        // This command requires a path to the project
        if (parent.projectPath == null) {
            Console.error("Project path is required. Use -p or --project to specify the path.");
            return 1;
        }

        //============ Metric business logic ============
        MetricContext ctx = Main.ctx;
        DitMetric metric = new DitMetric();
        DitResult result = metric.compute(ctx);

        //============ Display logic ============
        System.out.println(result.output());
        
        return 0;
    }

    @Override
    public boolean exceededThreshold(Object result) {
        return exceededThresholdResult;
    }
}
