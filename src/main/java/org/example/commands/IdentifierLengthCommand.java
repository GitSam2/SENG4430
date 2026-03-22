package org.example.commands;

import org.example.Main;
import org.example.QualityToolCLI;
import org.example.services.MetricContext;
import org.example.services.IdLength.IdLengthMetric;
import org.example.services.IdLength.IdLengthResult;
import org.example.utils.Console;

import picocli.CommandLine.*;

@Command(
        name = "id",
        aliases = {},
        mixinStandardHelpOptions = true,
        version = "qualitytool 1.0",
        description = {
                "Identifier Length Analysis"
        },
        footer = {
            "",
            "  @|bold Examples:|@",
            "    qualitytool id -p ./project --no-color",
            "    qualitytool id -p ./project",
            ""
        }
)
public class IdentifierLengthCommand extends BaseMetricCommand {
    @ParentCommand
    QualityToolCLI parent;

    @Override
    public String displayName() {
        return "Identifier Length Analysis";
    }

    @Override
    public Integer execute() throws Exception {
        //============ Parse options ============

        //============ Metric business logic ============
        MetricContext ctx = Main.ctx;
        IdLengthMetric metric = new IdLengthMetric();
        IdLengthResult result = metric.compute(ctx);
        //============ Display logic ============
        System.out.println(result.output());

        return 0;
    }

    // This is a placeholder. You can implement logic to determine if the result exceeds the threshold.
    @Override
    public boolean exceededThreshold(Object result) {
        return false;
    }
}
