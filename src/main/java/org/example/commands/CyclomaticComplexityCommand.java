package org.example.commands;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import org.example.JavaParserProvider;
import org.example.QualityToolCLI;
import org.example.services.CyclomaticComplexity.CyclomaticComplexity;
import org.example.services.CyclomaticComplexity.CyclomaticComplexityResult;
import org.example.utils.Console;
import picocli.CommandLine.*;

import java.io.IOException;
import java.util.List;

@Command(
        name = "cc",
        aliases = {},
        mixinStandardHelpOptions = true,
        version = "qualitytool 1.0",
        description = {
                "Cyclomatic Complexity Analysis"
        },
        footer = {
            "",
            "  @|bold Examples:|@",
            "    qualitytool cc -p ./project --no-color",
            "    qualitytool cc -p ./project -t -T 20 -W 10",
            ""
        }
)
public class CyclomaticComplexityCommand extends BaseMetricCommand {
    @ParentCommand
    QualityToolCLI parent;

    @Override
    public String displayName() {
        return "Cyclomatic Complexity Analysis";
    }

    @Option(names = {"-T", "--threshold"},
            description = "Threshold before cyclomatic complexity fails",
            defaultValue = "20"
    )
    String failureThreshold;

    @Option(names = {"-W", "--warning"},
            description = "Threshold before cyclomatic complexity is considered a warning",
            defaultValue = "10"
    )
    String warningThreshold;

    boolean exceededThresholdResult = false;

    @Override
    public Integer execute() throws Exception {
        //============ Parse options ============
        int warningThresholdNumber = 0;
        int failureThresholdNumber = 0;
        try {
            warningThresholdNumber = Integer.parseInt(warningThreshold);
            failureThresholdNumber = Integer.parseInt(failureThreshold);
        } catch (NumberFormatException e) {
            Console.error("Threshold numbers must be a integers");
            return 1;
        }

        //============ Metric business logic ============
        List<ParseResult<CompilationUnit>> parsedFiles;
        try {
            parsedFiles = parseProject();
            if (parsedFiles.isEmpty()) {
                Console.error("Project path contains no java files!");
                return 1;
            }
        } catch (IOException e) {
            Console.error("Could not parse project files!");
            return 1;
        }

        CyclomaticComplexity cc = new CyclomaticComplexity();

        //============ Display logic ============
        // Headers of the table columns
        String[] headers = {
                "File",
                "Average",
                "Highest Score",
                "Severity"
        };

        // Widths in characters of each column
        // Currently terminal width with columns is determined by hand
        int[] widths = {
                Console.terminalWidth() - 31,
                8,
                14,
                9
        };

        List<CyclomaticComplexityResult> result = cc.calculateCyclomaticComplexity(parsedFiles, warningThresholdNumber, failureThresholdNumber);
        // Create a row X column table of cells below the header row
        String[][] rows = new String[result.size()][headers.length];
        int rowIndex = 0;
        for (CyclomaticComplexityResult cyclomaticComplexityResult : result) {
            rows[rowIndex][0] = cyclomaticComplexityResult.getFileName();
            rows[rowIndex][1] = "%.1f".formatted(cyclomaticComplexityResult.getAverageScore());
            rows[rowIndex][2] = String.valueOf(cyclomaticComplexityResult.getHighestScore());

            switch(cyclomaticComplexityResult.getSeverity()) {
                case INFO -> rows[rowIndex][3] = Console.cyan("INFO");
                case WARNING -> rows[rowIndex][3] = Console.yellow("WARNING");
                case ERROR -> {
                    rows[rowIndex][3] = Console.boldRed("ERROR");
                    exceededThresholdResult = true;
                }
            }

            rowIndex++;
        }

        System.out.println(Console.table(headers, widths, rows));

        return 0;
    }

    @Override
    public boolean exceededThreshold(Object result) {
        return exceededThresholdResult;
    }

    public static List<ParseResult<CompilationUnit>> parseProject() throws IOException {
        SourceRoot parser = JavaParserProvider.getInstance();
        return parser.tryToParse();
    }
}
