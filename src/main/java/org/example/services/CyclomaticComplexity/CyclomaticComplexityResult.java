package org.example.services.CyclomaticComplexity;

public class CyclomaticComplexityResult {
    public enum Severity {
        ERROR,
        WARNING,
        INFO
    }

    private String fileName;
    private double averageScore;
    private int highestScore;
    private String highestName;
    private Severity severity;

    public CyclomaticComplexityResult(String fileName, double averageScore, int highestScore, String highestName, Severity severity) {
        this.fileName = fileName;
        this.averageScore = averageScore;
        this.highestScore = highestScore;
        this.highestName = highestName;
        this.severity = severity;
    }

    public double getAverageScore() {
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

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
