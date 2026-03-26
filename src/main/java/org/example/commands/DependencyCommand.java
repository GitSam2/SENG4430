package org.example.commands;

import java.util.concurrent.CompletableFuture;

import org.apache.maven.model.Dependency;
import org.example.Main;
import org.example.QualityToolCLI;
import org.example.services.MetricContext;
import org.example.services.dependencies.DependencyMetric;
import org.example.services.dependencies.DependencyResult;
import picocli.CommandLine.*;

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

    @Override
    public String displayName() {
        return "Dependency Analysis";
    }

    @Override
    public Integer execute() throws Exception {
        //============ Parse options ============

        //============ Metric business logic ============
        MetricContext ctx = Main.ctx;
        DependencyMetric metric = new DependencyMetric();

        // Kick off compute in a background thread
        CompletableFuture<DependencyResult> future = CompletableFuture.supplyAsync(() -> metric.compute(ctx));

        // Render loop on the main thread
        int tick = 0;
        String[] throbber = { "|", "/", "-", "\\" };

        while (!future.isDone()) {
            String spinner = throbber[tick % throbber.length];
            String status  = getStatus(metric);

            // \r rewrites the current line in a TUI
            System.out.print("\r" + spinner + "  " + status + "   ");
            System.out.flush();

            tick++;
            Thread.sleep(100); // poll every 100ms
        }
        DependencyResult result = future.get();

        //============ Display logic ============
        System.out.println();
        System.out.println(result.output());
        
        return 0;
    }

    @Override
    public boolean exceededThreshold(Object result) {
        return false;
    }

    private String getStatus(DependencyMetric task) {
        if (task.fetchingCves)       return "Fetching CVE information for dependencies...";
        if (task.resolving)          return "Resolving dependency tree...";
        if (task.bootstrapping)      return "Bootstrapping maven...";
        
        return "Starting...";
    }
}
