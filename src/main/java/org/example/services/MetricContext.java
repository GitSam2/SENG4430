package org.example.services;

import com.github.javaparser.ast.CompilationUnit;

import java.util.List;

public final class MetricContext {
    private final List<CompilationUnit> compilationUnits;



    public MetricContext(List<CompilationUnit> compilationUnits) {
        this.compilationUnits = List.copyOf(compilationUnits);
    }

    public List<CompilationUnit> compilationUnits() {
        return compilationUnits;
    }

}
