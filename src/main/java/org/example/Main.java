package org.example;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {
    static void main(String[] args) throws Exception {
//        Path testInputPath = Path.of("src");        // test path
////        Path path = Path.of(args[0]);       // how It's supposed to be or smt similar
//        JavaParserProvider.initialization(testInputPath);
        // RepositorySystem system = MavenBootstrap.newRepositorySystem();
        // RepositorySystemSession session = MavenBootstrap.newSession(system);

        // DependencyTreeResolver resolver =
        //         new DependencyTreeResolver(system, session);

        // List<DependencyTree> trees =
        //         resolver.resolvePom("pom.xml");

        // trees.forEach(TreePrinter::print);
        VersionMetadataService version = new VersionMetadataService();
        IO.println(version.fetchLatestVersion("org.junit.jupiter", "junit-jupiter"));
    }

    // Example of how to use the parser
    public static List<ParseResult<CompilationUnit>> parserProject(Path projectPath) throws IOException {
        SourceRoot parser = JavaParserProvider.getInstance();
        List<ParseResult<CompilationUnit>> resultList = parser.tryToParse();        // this is a list of ParseResult<CU>, meaning its a result of the java files (success or failed)
        resultList.forEach(result -> {
            if (result.isSuccessful() && result.getResult().isPresent()) {
                CompilationUnit cU = result.getResult().get();
                System.out.println(cU);
            }
        });

        // In order to get the data, check the result first, then use result.get() to get the compilation unit which is the parsed java file
        // All the additional comments so you can understand and utilize the input from the repo, I will delete later.
        return resultList;
    }
}
