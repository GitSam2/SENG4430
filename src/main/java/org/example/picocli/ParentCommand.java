package org.example.picocli;

import java.nio.file.Path;
import java.util.concurrent.Callable;

import org.example.QualityToolApp;
import org.example.services.MetricContext;
import org.example.services.ProjectParser;

import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ScopeType;
import picocli.CommandLine.Spec;

@CommandLine.Command(name = "qualitytool", mixinStandardHelpOptions = true, version = "qualitytool 1.0", description = {
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
})
public class ParentCommand implements Callable<Integer> {
    @Spec
    CommandSpec spec;
    @Option(names = { "-p",
            "--project" }, description = "Root path of the project to analyse (default: current directory)", defaultValue = ".", scope = ScopeType.INHERIT)
    public Path projectPath;

    @Option(names = { "--no-color",
            "--no-colour" }, description = "Disable ANSI color output", scope = ScopeType.INHERIT)
    public boolean noColor;

    @Option(names = { "-t",
            "--fail-threshold" }, description = "Enable error on breaching threshold. Use --threshold in each subcommand to give one.", scope = ScopeType.INHERIT)
    public boolean failThreshold;

    @Override
    public Integer call() throws Exception {
        // This method will be called when QualityToolApp() is executed
        Console.init(spec, noColor);

        CommandLine commandLine = spec.commandLine();
        // If no subcommand is provided, display usage and exit with error code
        if (!commandLine.getParseResult().hasSubcommand()) {
            commandLine.usage(System.err);
            return 1;
        }

        // Initialize the MetricContext with the parsed project
        ProjectParser parser = new ProjectParser();
        QualityToolApp.ctx = new MetricContext(projectPath, parser.parseProject(projectPath));

        return 0;
    }
}
