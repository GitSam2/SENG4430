package org.example;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main(String[] args) {
        Path testInputPath = Path.of("src");        // test path
//        Path path = Path.of(args[0]);       // how its supposed to be or smt similar
        JavaParserProvider.initialization(testInputPath);
    }

    // Example of how to use the parser
    public List<ParseResult<CompilationUnit>> parserProject(Path projectPath) throws IOException {
        SourceRoot parser = JavaParserProvider.getInstance();
        List<ParseResult<CompilationUnit>> resultList = parser.tryToParse();        // this is a list of ParseResult<CU>, meaning its a result of the java files (success or failed)
        resultList.forEach(result -> {
            if (result.isSuccessful() && result.getResult().isPresent()) {
                CompilationUnit cU = result.getResult().get();
                System.out.println(cU.toString());
            }
        });
        
        // In order to get the data, check the result first, then use result.get() to get the compilation unit which is the parsed java file
        // All the additional comments so you can understand and utilize the input from the repo, I will delete later.
        return parser.tryToParse();
    }
}
