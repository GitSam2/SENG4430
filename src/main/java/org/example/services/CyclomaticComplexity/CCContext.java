package org.example.services.CyclomaticComplexity;

public class CCContext {
    public enum Severity {
        ERROR,
        WARNING,
        INFO
    }

    private final String fileName;
    private final Double averageScore;
    private final int highestScore;
    private final String highestName;
    private CCContext.Severity severity;

    public CCContext(String fileName, Double averageScore, int highestScore, String highestName, Severity severity) {
        this.fileName = fileName;
        this.averageScore = averageScore;
        this.highestScore = highestScore;
        this.highestName = highestName;
        this.severity = severity;
    }

    // Getters and Setters
    public Double getAverageScore() {
        return averageScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public String getHighestName() {
        return highestName;
    }

    public CCContext.Severity getSeverity() {
        return severity;
    }

    public String getFileName() {
        return fileName;
    }

}
