package org.example;

import java.util.List;

/// Model class to hold info after a dependency is queried against maven and cve apis
public record DependencyInfo(List<String> cves, String latestVersion, boolean isOutdated) {}
