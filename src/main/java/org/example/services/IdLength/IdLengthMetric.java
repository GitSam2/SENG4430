package org.example.services.IdLength;

import java.util.List;

import org.example.services.Metric;
import org.example.services.MetricContext;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;

public final class IdLengthMetric implements Metric<IdLengthResult> {

    private int totalIdentifiers;
    private int maxIdentifierLength;
    private int totalLength;

    @Override
    public String id() {
        return "IdLength";
    }

    @Override
    public IdLengthResult compute(MetricContext ctx) {
        if (ctx == null) {
            throw new IllegalStateException("SourceRoot is not initialized!");
        }

        IdLengthResult result = extractIdentifiers(ctx.compilationUnits());

        return result;
    }

    // Extract identifiers from the compilation unit and process them
    private IdLengthResult extractIdentifiers(List<CompilationUnit> cuList) {
        for (CompilationUnit cu : cuList) {
            cu.findAll(ClassOrInterfaceDeclaration.class)
                    .forEach(c -> processIdentifier(c.getNameAsString()));

            cu.findAll(MethodDeclaration.class)
                    .forEach(m -> processIdentifier(m.getNameAsString()));

            cu.findAll(FieldDeclaration.class)
                    .forEach(f -> f.getVariables()
                            .forEach(v -> processIdentifier(v.getNameAsString())));

            cu.findAll(VariableDeclarator.class)
                    .forEach(v -> processIdentifier(v.getNameAsString()));

            cu.findAll(Parameter.class)
                    .forEach(p -> processIdentifier(p.getNameAsString()));
        }

        return new IdLengthResult(totalIdentifiers, maxIdentifierLength, totalLength);
    }

    // Process an identifier by updating the statistics
    private void processIdentifier(String identifier) {
        int length = identifier.length();
        totalIdentifiers++;
        totalLength += length;

        if (length > maxIdentifierLength) {
            maxIdentifierLength = length;
        }
    }
}
