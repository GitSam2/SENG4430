package org.example;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void main(String[] args) throws IOException {
        Path testInputPath = Path.of("src");        // test path
        Scanner scanner = new Scanner(System.in);
//        Path path = Path.of(args[0]);       // how It's supposed to be or smt similar
        JavaParserProvider.initialization(testInputPath);           // replace w real path later
        Main main = new Main();
        main.metricSelection(scanner);
    }

    public static List<ParseResult<CompilationUnit>> parseProject() throws IOException {
        SourceRoot parser = JavaParserProvider.getInstance();
        return parser.tryToParse();
    }

    public void metricSelection(Scanner scanner) throws IOException {
//        if (projectPath == null) {                        // prolly need to make a project path check bcs what if they feed dumb path
//            // either exit or attempt to reread this
//            System.out.println("ProjectPath is null");
//            return;
//        }
//
//        if (!projectPath.startsWith("src")) {           // can trace back to src root from one of the path library using the current path
//            System.out.println("ProjectPath is not source root");
//            return;
//        }
//        System.out.println("ProjectPath is " + projectPath);
        System.out.println("Welcome to watevs this thing is, please choose metric,...etc, input n1 for Cyclomatic complexity, n2 for, n3 for,..., -1 to exit");
        int userInput = scanner.nextInt();

        while (userInput != -1) {           // -1 = exit program
            switch (userInput) {
                case 1:
                    List<ParseResult<CompilationUnit>> parsedFiles = parseProject();
                    // call metric method from its class here
                    break;
                case 2:
                    // whatevs your metric call is idk
                default:
                    System.out.println("Welcome to watevs this thing is, please choose metric,...etc, input n1 for Cyclomatic complexity, n2 for, n3 for,..., -1 to exit");
                    break;
            }
            userInput = scanner.nextInt();
        }

    }
}
