package org.example.commands;

import org.example.QualityToolCLI;
import org.example.services.MetricContext;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
        name = "all",
        mixinStandardHelpOptions = true,
        description = {"Run all available quality analyses"}
)
public class AllCommand extends BaseMetricCommand {
        @Spec Model.CommandSpec spec;

        @Override
        public String displayName() {
                return "All Quality Metrics";
        }

        /** Reflectively set the @ParentCommand field so subcommands can access parent.projectPath etc. */
        private void injectParent(Object cmd) throws Exception {
                for (var field : cmd.getClass().getDeclaredFields()) {
                        if (field.isAnnotationPresent(ParentCommand.class)) {
                                field.setAccessible(true);
                                field.set(cmd, parent);
                        }
                }
        }

        @Override
        public Object execute() throws Exception {
                CommandLine root = spec.commandLine().getParent();

                // Grab every sibling subcommand except "all" itself
                for (CommandLine sub : root.getSubcommands().values()) {
                        if (sub.getCommandName().equals("all")) continue;

                        // Pass the inherited parent reference into the subcommand instance
                        Object cmd = sub.getCommand();
                        if (cmd instanceof Callable<?> callable) {
                                injectParent(cmd);
                                Integer exitCode = (Integer) callable.call();
                                if (exitCode != 0) return exitCode;   // fail-fast, or collect & continue
                        }
                }
                return 0;
        }

        @Override
        public boolean exceededThreshold(Object result) {
                return false;
        }
}
