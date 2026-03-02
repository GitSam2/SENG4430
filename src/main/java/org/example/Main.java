/** Title: Main.java
*   @author Troy Madden
*   Created: 18th February, 2026
*   @version 1.3
*   Description: Created for Assignment 1 SENG4430. Group Assignment testing the software quality for a power plant. This class contains the 
*   main method for running the program and determines the source path for the parser.
*/

package org.example;

import java.nio.file.Path;

import org.example.IdentifierLength.IdentifierAnalyzer;
import org.example.IdentifierLength.IdentifierReport;
import org.example.IdentifierLength.IdentifierThreshold;

import com.github.javaparser.utils.SourceRoot;

public class Main {

    public static void main(String[] args) {

        // Path to source code
        Path sourcePath = Path.of("powsybl-open-loadflow/src/main/java");

        // Initialise JavaParser
        JavaParserProvider.initialization(sourcePath);
        SourceRoot sourceRoot = JavaParserProvider.getInstance();

        // Run identifier analysis class
        IdentifierAnalyzer analyzer = new IdentifierAnalyzer();
        analyzer.analyzeProject(sourceRoot);

        // Run threshold evaluation class
        IdentifierThreshold threshold = new IdentifierThreshold(30);
        threshold.evaluateProject(sourceRoot);

        // Build and print report class
        IdentifierReport report = new IdentifierReport();

        System.out.println(report.buildAnalysisOutput(analyzer));
        System.out.println();
        System.out.println(report.buildThresholdOutput(threshold));
    }
}