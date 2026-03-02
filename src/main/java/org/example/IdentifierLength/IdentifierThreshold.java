/** Title: IdentifierThreshold.java
*   @author Troy Madden
*   Created: 18th February, 2026
*   @version 1.3
*   Description: Created for Assignment 1 SENG4430. Group Assignment testing the software quality for a power plant. This class imports the
*   statistics and uses them to create rules and pass the rules onto the reporter class. 
*/

package org.example.IdentifierLength;

import java.io.IOException;
import java.util.List;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.utils.SourceRoot;

public class IdentifierThreshold {

    private final int maxAllowedLength;
    private int exceededCount; 

    public IdentifierThreshold(int maxAllowedLength) {
        this.maxAllowedLength = maxAllowedLength;
        this.exceededCount = 0;
    }

    public void evaluateProject(SourceRoot sourceRoot) {
    exceededCount = 0;

    if (sourceRoot == null) {
        throw new IllegalStateException("SourceRoot is not initialized!"); // Check if sourceRoot is initialized before proceeding
    }

    try {
        List<ParseResult<CompilationUnit>> results = sourceRoot.tryToParse(); // Try to parse all files in the source root

        for (ParseResult<CompilationUnit> result : results) {
            if (result.isSuccessful() && result.getResult().isPresent()) {
                result.getResult().get()
                        .findAll(VariableDeclarator.class)
                        .forEach(v -> {
                            if (v.getNameAsString().length() > maxAllowedLength) {
                                exceededCount++;
                            }
                        });
            }
        }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse source files", e); // If there is an error parsing the files, throw a runtime exception
        }
    }

    // Getters
    public int getExceededCount() {
        return exceededCount;
    }

    public int getMaxAllowedLength() {
        return maxAllowedLength;
    }
}