package org.example.services.dependencies;

public class NodeResult {
    private final boolean isDirectDependency;
    private final boolean hasCve;
    private final double severity;
    private final String fixedVersion;
    private final String id;

    public NodeResult(String id, boolean isDirectDependency, boolean hasCve, double severity, String fixedVersion) {
        this.id = id;
        this.isDirectDependency = isDirectDependency;
        this.hasCve = hasCve;
        this.severity = severity;
        this.fixedVersion = fixedVersion;
    }

    public String id() {
        return id;
    }

    public boolean isDirectDependency() {
        return isDirectDependency;
    }

    public boolean hasCve() {
        return hasCve;
    }

    public double severity() {
        return severity;
    }

    public String fixedVersion() {
        return fixedVersion;
    }
}
