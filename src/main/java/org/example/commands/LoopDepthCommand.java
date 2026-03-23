package org.example.commands;

import org.example.Main;
import org.example.QualityToolCLI;
import org.example.services.MetricContext;
import org.example.services.NestedDepth.LoopDepthMetric;
import org.example.services.NestedDepth.LoopMetrics;
import org.example.utils.Console;

import picocli.CommandLine.*;



@Command(
        name = "ld",
        aliases = {},
        mixinStandardHelpOptions = true,
        version = "qualitytool 1.0",
        description = {
                "Loop Depth Analysis"
        }
)
public class LoopDepthCommand extends BaseMetricCommand {
    boolean exceededThresholdResult = false;

    @ParentCommand
    QualityToolCLI parent;

    @Override
    public String displayName() {
        return "Loop Depth Analysis";
    }

    @Override
    public Integer execute() throws Exception {
        //============ Parse options ============

        //============ Metric business logic ============
        MetricContext ctx = Main.ctx;
        LoopDepthMetric metric = new LoopDepthMetric();
        LoopMetrics result = metric.compute(ctx);

        //============ Display logic ============
        System.out.println("LOOP DEPTH ANALYSIS");
        System.out.println(Console.divider('─'));

        System.out.println("# of loops: " + result.loopCount);
        System.out.println("Max loop depth: " + result.maxDepth);
        System.out.println("Average loop depth: " + result.average());
        System.out.println("Flagged loops: " + result.flaggedCount);

        if (result.flaggedCount > 0) {
            Console.error("Some loops exceeded the warning threshold!");
            System.out.println(Console.divider('─'));
            return 1;
        }
        
        Console.success("No flagged loops");
        System.out.println(Console.divider('─'));

        return 0;
    }

    @Override
    public boolean exceededThreshold(Object result) {
        return exceededThresholdResult;
    }
}
