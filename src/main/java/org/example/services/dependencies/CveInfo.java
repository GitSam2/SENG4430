package org.example.services.dependencies;

public record CveInfo(String id, String summary, String details, double cvsScore, String fixedVersion) {}
