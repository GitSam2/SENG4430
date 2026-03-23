package org.example;

import org.example.commands.*;
import org.example.services.MetricContext;
import org.example.services.ProjectParser;
import org.example.utils.Console;
import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.CommandLine.Model.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(name = "qualitytool",
        mixinStandardHelpOptions = true,
        version = "qualitytool 1.0",
        description = {
            "",
            "@|bold,cyan  ██████╗ ██╗   ██╗ █████╗ ██╗     ██╗████████╗██╗   ██╗|@",
            "@|bold,cyan ██╔═══██╗██║   ██║██╔══██╗██║     ██║╚══██╔══╝╚██╗ ██╔╝|@",
            "@|bold,cyan ██║   ██║██║   ██║███████║██║     ██║   ██║    ╚████╔╝  |@",
            "@|bold,cyan ██║▄▄ ██║██║   ██║██╔══██║██║     ██║   ██║     ╚██╔╝   |@",
            "@|bold,cyan ╚██████╔╝╚██████╔╝██║  ██║███████╗██║   ██║      ██║    |@",
            "@|bold,cyan  ╚══▀▀═╝  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝    |@",
            "@|bold,cyan                      TOOL                               |@",
            "",
            "  @|italic Software Quality Analysis CLI|@ — run one, many, or @|bold --all|@ checks.",
            ""
        },
//        AllCommand.class
        subcommands = {CyclomaticComplexityCommand.class, DitCommand.class, IdentifierLengthCommand.class, LoopDepthCommand.class, WMCCommand.class},
        subcommandsRepeatable = true,
        footer = {
            "",
            "  @|bold Examples:|@",
            "    qualitytool cc -p ./my-project",
            ""
        }
        )
public class QualityToolCLI implements Callable<Integer> {
    @Option(names = {"-p", "--project"},
            description = "Root path of the project to analyse (default: current directory)",
            defaultValue = ".",
            scope = ScopeType.INHERIT)
    public Path projectPath;

    @Option(names = {"--no-color", "--no-colour"},
            description = "Disable ANSI color output",
            scope = ScopeType.INHERIT)
    public boolean noColor;

    @Option(names = {"-t", "--fail-threshold"},
            description = "Enable error on breaching threshold. Use --threshold in each subcommand to give one.",
            scope = ScopeType.INHERIT)
    public boolean failThreshold;

//    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
//    private boolean helpRequested = false;

    @Spec CommandSpec spec;

    /// Entrypoint into command
    @Override
    public Integer call() throws IOException {
        Console.init(spec, noColor);

        CommandLine commandLine = spec.commandLine();
        if (!commandLine.getParseResult().hasSubcommand()) {
            commandLine.usage(System.err);
            return 1;
        }

        // Initialise the java parser once
        JavaParserProvider.initialization(projectPath);

        // this needs to be changed because we can't do this twice but for the moment I'm adding this:
        ProjectParser parser = new ProjectParser();
        Main.ctx = new MetricContext(parser.parseProject(projectPath));

        return 0;
    }
}
