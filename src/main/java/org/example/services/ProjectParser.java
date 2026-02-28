package org.example.services;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class ProjectParser {

    public List<CompilationUnit> parseProject(Path projectRoot) throws IOException {
        SourceRoot sourceRoot = new SourceRoot(projectRoot);

        List<CompilationUnit> units = new ArrayList<>();

        for (ParseResult<CompilationUnit> result : sourceRoot.tryToParse()) {
            result.getResult().ifPresent(units::add);
        }

        return units;
    }
}