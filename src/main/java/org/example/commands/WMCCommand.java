package org.example.commands;

import org.example.Main;
import org.example.QualityToolCLI;
import org.example.services.MetricContext;
import org.example.services.wmc.WmcMetric;
import org.example.services.wmc.WmcResult;

import picocli.CommandLine.*;



@Command(
        name = "wmc",
        aliases = {},
        mixinStandardHelpOptions = true,
        version = "qualitytool 1.0",
        description = {
                "Weight of a Module (WMC) Analysis"
        }
)
public class WMCCommand extends BaseMetricCommand {
    boolean exceededThresholdResult = false;

    @ParentCommand
    QualityToolCLI parent;

    @Override
    public String displayName() {
        return "Weight of a Module (WMC) Analysis";
    }

    @Override
    public Integer execute() throws Exception {
        //============ Parse options ============

        //============ Metric business logic ============
        MetricContext ctx = Main.ctx;
        WmcMetric metric = new WmcMetric();
        WmcResult result = metric.compute(ctx);

        //============ Display logic ============
        System.out.println(result.output());
        
        return 0;
    }

    @Override
    public boolean exceededThreshold(Object result) {
        return exceededThresholdResult;
    }
}
