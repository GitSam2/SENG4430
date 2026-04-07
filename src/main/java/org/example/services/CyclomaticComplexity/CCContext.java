package org.example.services.CyclomaticComplexity;

public class CCContext {
    public enum Severity {
        ERROR,
        WARNING,
        INFO
    }

    private String fileName;
    private Double averageScore;
    private int highestScore;
    private String highestName;
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

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public String getHighestName() {
        return highestName;
    }

    public void setHighestName(String highestName) {
        this.highestName = highestName;
    }

    public CCContext.Severity getSeverity() {
        return severity;
    }

    public void setSeverity(CCContext.Severity severity) {
        this.severity = severity;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
