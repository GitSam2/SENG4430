/** Title: IdLengthMetric.java
*   @author Troy Madden
*   Created: 18th February, 2026
*   @version 1.4
*   Description: Created for Assignment 1 SENG4430. Group Assignment testing the software quality for a 
*   power plant. This class takes the adds up the identifiers from a chosen source root.
*/

package org.example.services.IdLength;

import java.util.List;

import org.example.services.Metric;
import org.example.services.MetricContext;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;

public final class IdLengthMetric implements Metric<IdLengthResult> {

    private static final int MAX_ALLOWED_LENGTH = 30; // threshold for identifier length
    private int totalIdentifiers; // total number of identifiers being analysed
    private int maxIdentifierLength; // the longest identifiers length
    private int totalLength; // total length of all identifiers combined
    private int exceedsMaxLength; // how many identifiers exceed 30 characters

    @Override
    public String id() {
        return "IdLength";
    }

    @Override
    public IdLengthResult compute(MetricContext ctx) {

        if (ctx == null) {
            throw new IllegalStateException("SourceRoot is not initialized!");
        }

        // reset counters is important if metric runs multiple times
        totalIdentifiers = 0;
        maxIdentifierLength = 0;
        totalLength = 0;
        exceedsMaxLength = 0;

        extractIdentifiers(ctx.compilationUnits());

        return new IdLengthResult(
                totalIdentifiers,
                maxIdentifierLength,
                totalLength,
                exceedsMaxLength
        );
    }

    // extract identifiers from the compilation unit and process them
    private void extractIdentifiers(List<CompilationUnit> cuList) {
        for (CompilationUnit cu : cuList) {
            cu.findAll(ClassOrInterfaceDeclaration.class)
                    .forEach(c -> processIdentifier(c.getNameAsString()));

            cu.findAll(MethodDeclaration.class)
                    .forEach(m -> processIdentifier(m.getNameAsString()));

            cu.findAll(VariableDeclarator.class)
                    .forEach(v -> processIdentifier(v.getNameAsString()));

            cu.findAll(Parameter.class)
                    .forEach(p -> processIdentifier(p.getNameAsString()));
        }
    }

    // process an identifier by updating the counters
    private void processIdentifier(String identifier) {
        int length = identifier.length();
        totalIdentifiers++;
        totalLength += length;

        if (length > maxIdentifierLength) {
            maxIdentifierLength = length;
        }

        if (length > MAX_ALLOWED_LENGTH) {
            exceedsMaxLength++;
        }
    }
}
