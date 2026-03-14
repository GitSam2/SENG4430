package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeightedMethodsPerClass {

    public static int calculateWMC(Path filePath) throws IOException {

        int methodCount = 0;

        Pattern methodPattern = Pattern.compile(
                "(public|private|protected)?\\s*(static\\s+)?\\w+\\s+\\w+\\s*\\([^)]*\\)\\s*\\{"
        );

        for (String line : Files.readAllLines(filePath)) {

            Matcher matcher = methodPattern.matcher(line.trim());

            if (matcher.find()) {
                methodCount++;
            }
        }

        return methodCount;
    }

}
