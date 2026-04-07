package org.example.commands;

import java.util.*;

import org.example.Main;
import org.example.QualityToolCLI;
import org.example.services.MetricContext;
import org.example.services.wmc.WmcMetric;
import org.example.services.wmc.WmcResult;
import org.example.utils.Console;

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
        List<String> lines = metric.computeStrings(ctx);
        WmcResult result = metric.compute(ctx);
        

        //============ Display logic ============
       List<String[]> rowsList = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\|");
            String className = parts[0].replace("Class:", "").trim();
            String wmc = parts[1].replace("WMC:", "").trim();
            rowsList.add(new String[] { className, wmc });
        }

        String[][] rows = rowsList.toArray(new String[0][]);
        int maxClassLength = "class".length();
        for (String[] row : rows){
            maxClassLength = Math.max(maxClassLength, row[0].length());
        }
        String[] headers = { "Class", "WMC" };
        int[] widths = { maxClassLength + 2, 10};
        System.out.println(Console.table(headers, widths, rows));

        System.out.println("Average WMC: "+ result.getMeanWMC());
        
        return 0;
    }

    @Override
    public boolean exceededThreshold(Object result) {
        return exceededThresholdResult;
    }
}
