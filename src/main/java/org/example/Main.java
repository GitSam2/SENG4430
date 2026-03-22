package org.example;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import org.example.services.MetricContext;
import picocli.CommandLine;

import java.io.IOException;
import java.util.List;

public class Main {
    public static MetricContext ctx;


    public static void main(String... args) {
        CommandLine cmd = new CommandLine(new QualityToolCLI());
        cmd.setExecutionStrategy(new CommandLine.RunAll());
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }

    public static List<ParseResult<CompilationUnit>> parseProject() throws IOException {
        SourceRoot parser = JavaParserProvider.getInstance();
        return parser.tryToParse();
    }
}
