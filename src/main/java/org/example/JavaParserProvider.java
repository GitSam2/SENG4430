package org.example;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.utils.SourceRoot;

import java.nio.file.Path;

public class JavaParserProvider {

    private static SourceRoot instance;

    private JavaParserProvider() {}

    // Call this only once at the start of the program
    public static void initialization(Path dirPath) {
        if (instance != null) {
            throw new IllegalStateException("SourceRoot is already initialized!");
        }
        // Build the configuration for parser
        ParserConfiguration config = new ParserConfiguration()
                .setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_21);

        // Initialize the singleton with that config
        instance = new SourceRoot(dirPath, config);
    }

    public static SourceRoot getInstance(){
        return instance;
    }
}
