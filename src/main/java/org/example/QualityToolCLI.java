package org.example;

import org.example.commands.AllCommand;
import org.example.commands.DependencyCommand;
import org.example.commands.TestCommandOne;
import org.example.commands.TestCommandTwo;
import org.example.utils.Console;
import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.CommandLine.Model.*;

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
        subcommands = {TestCommandOne.class, TestCommandTwo.class, DependencyCommand.class, AllCommand.class},
        subcommandsRepeatable = true,
        footer = {
            "",
            "  @|bold Examples:|@",
            "    qualitytool --all -p ./my-project",
            "    qualitytool cyclomatic inheritance -p ./src",
            "    qualitytool dependency --cves --transitive -p ./my-project",
            "    qualitytool loc coupling -p ./src --output report.json",
            ""
        }
        )
public class QualityToolCLI implements Callable<Integer> {
    @Option(names = {"-p", "--project"},
            description = "Root path of the project to analyse (default: current directory)",
            defaultValue = ".",
            scope = ScopeType.INHERIT)
    public Path projectPath;

    @Option(names = {"--no-color"},
            description = "Disable ANSI color output",
            scope = ScopeType.INHERIT)
    public boolean noColor;

    @Option(names = {"--fail-threshold"},
            description = "Enable error on breaching threshold. Use --threshold in each subcommand to give one.")
    public boolean failThreshold;

    @Spec CommandSpec spec;

    /// Entrypoint into command
    @Override
    public Integer call() throws Exception {
        Console.init(spec, noColor);

        CommandLine commandLine = spec.commandLine();
        if (!commandLine.getParseResult().hasSubcommand()) {
            commandLine.usage(System.err);
            return 1;
        }

        return 0;
    }
}
