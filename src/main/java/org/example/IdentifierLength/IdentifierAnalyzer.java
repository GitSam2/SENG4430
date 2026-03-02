/** Title: IdentifierAnalyzer.java
*   @author Troy Madden
*   Created: 18th February, 2026
*   @version 1.3
*   Description: Created for Assignment 1 SENG4430. Group Assignment testing the software quality for a power plant. This class imports the
*   java parser and created statistics used in rules and report creating.
*/

package org.example.IdentifierLength;

import java.io.IOException;
import java.util.List;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.utils.SourceRoot;

public class IdentifierAnalyzer {

    // What I need to track
    private int totalIdentifiers;
    private int maxIdentifierLength;
    private int totalLength;


    public void analyzeProject(SourceRoot sourceRoot) {
        reset();

    if (sourceRoot == null) {
        throw new IllegalStateException("SourceRoot is not initialized!");
    }

    try {
        List<ParseResult<CompilationUnit>> results = sourceRoot.tryToParse(); // Try to parse all files in the source root

        for (ParseResult<CompilationUnit> result : results) {
            if (result.isSuccessful() && result.getResult().isPresent()) {
                extractIdentifiers(result.getResult().get());
            }
        }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse source files", e); // If there is an error parsing the files, throw a runtime exception
        }
    }

    // Extract identifiers from the compilation unit and process them
    private void extractIdentifiers(CompilationUnit cu) { 
        cu.findAll(ClassOrInterfaceDeclaration.class)
                .forEach(c -> processIdentifier(c.getNameAsString()));

        cu.findAll(MethodDeclaration.class)
                .forEach(m -> processIdentifier(m.getNameAsString()));

        cu.findAll(FieldDeclaration.class)
                .forEach(f ->
                        f.getVariables()
                                .forEach(v -> processIdentifier(v.getNameAsString()))
                );

        cu.findAll(VariableDeclarator.class)
                .forEach(v -> processIdentifier(v.getNameAsString()));

        cu.findAll(Parameter.class)
                .forEach(p -> processIdentifier(p.getNameAsString()));
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

    private void reset() {
        totalIdentifiers = 0;
        maxIdentifierLength = 0;
        totalLength = 0;
    }

    // Getters for the statistics
    public int getTotalIdentifiers() {
        return totalIdentifiers;
    }

    public int getMaxIdentifierLength() {
        return maxIdentifierLength;
    }

    public double getAverageIdentifierLength() {
        return totalIdentifiers == 0
                ? 0.0
                : (double) totalLength / totalIdentifiers;
    }

    public int getTotalLength() {
        return totalLength;
    }
}