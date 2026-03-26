package org.example.services;

import com.github.javaparser.ast.CompilationUnit;

import java.nio.file.Path;
import java.util.List;

public final class MetricContext {
    private final Path projectPath;
    private final List<CompilationUnit> compilationUnits;

    public MetricContext(Path projectPath, List<CompilationUnit> compilationUnits) {
        this.projectPath = projectPath;
        this.compilationUnits = List.copyOf(compilationUnits);
    }

    public Path getProjectPath() {
        return projectPath;
    }

    public List<CompilationUnit> compilationUnits() {
        return compilationUnits;
    }

}
