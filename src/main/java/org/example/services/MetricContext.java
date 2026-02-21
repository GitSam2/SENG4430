package org.example.services;

import com.github.javaparser.ast.CompilationUnit;

public final class MetricContext {
    private final CompilationUnit compilationUnit;


    public MetricContext(CompilationUnit cu) { this.compilationUnit = cu; }
    public CompilationUnit compilationUnit() { return compilationUnit; }

}
