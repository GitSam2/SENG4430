package org.example.commands;

public interface MetricCommand {
    /// Human-readable name used in display headers
    public String displayName();

    /// Execute the metric command and generate report
    public Object execute() throws Exception;

    /// Whether this metric breached a threshold(enabled by --fail-threshold, configured --threshold)
    public boolean exceededThreshold(Object result);
}
