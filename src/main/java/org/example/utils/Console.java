package org.example.utils;

import picocli.CommandLine.Help.Ansi;
import picocli.CommandLine.Model.CommandSpec;

public class Console {

    /// Whether to format with ansi codes
    private static Ansi ansi = Ansi.AUTO;
    private static CommandSpec commandSpec;

    public static void init(CommandSpec spec, boolean colour) {
        commandSpec = spec;
        ansi = colour ? Ansi.AUTO : Ansi.OFF;
    }

    /// Get number of terminal columns
    public static int terminalWidth() {
        if (commandSpec == null) {
            return 80; // default width if not initialized
        }
        return commandSpec.usageMessage().width();
    }

    /// Format a string with ansi codes using picocli @|text|@ markup
    public static String style(String markup) {
        return ansi.string(markup);
    }

    // Colour methods
    public static String red    (String s) { return style("@|red "     + s + "|@"); }
    public static String green  (String s) { return style("@|green "   + s + "|@"); }
    public static String yellow (String s) { return style("@|yellow "  + s + "|@"); }
    public static String blue   (String s) { return style("@|blue "    + s + "|@"); }
    public static String magenta(String s) { return style("@|magenta " + s + "|@"); }
    public static String cyan   (String s) { return style("@|cyan "    + s + "|@"); }

    // Styling methods
    public static String bold     (String s) { return style("@|bold "      + s + "|@"); }
    public static String italic   (String s) { return style("@|italic "    + s + "|@"); }
    public static String underline(String s) { return style("@|underline " + s + "|@"); }

    // Combination methods
    public static String boldRed  (String s) { return style("@|bold,red "   + s + "|@"); }
    public static String boldGreen(String s) { return style("@|bold,green " + s + "|@"); }

    // Pre-formatted message types
    public static void error  (String msg) { System.err.println(boldRed("✗ " + msg)); }
    public static void success (String msg) { System.out.println(boldGreen("✓ " + msg)); }
    public static void warn   (String msg) { System.out.println(yellow("⚠ " + msg)); }
    public static void info   (String msg) { System.out.println(cyan("ℹ " + msg)); }

    /**
     * Render a table with styled headers and wrapped columns.
     *
     * @param headers  Column header labels
     * @param widths   Column widths in characters (should sum to <= terminalWidth())
     * @param rows     Data rows; each String may contain @|...|@ markup
     */
    public static String table(String[] headers, int[] widths, String[][] rows) {
        StringBuilder sb = new StringBuilder();

        // Create format string for each row
        String rowPattern;
        {
            StringBuilder rowBuilder = new StringBuilder();
            for (int width : widths) {
                rowBuilder.append("%-").append(width).append("s|").append("%n");
            }
            rowPattern = rowBuilder.toString();
        }

        // Print header of table
        sb.append(rowPattern.formatted((Object[]) headers));
        sb.append(divider());

        // Print body of table
        int widthsIndex = 0;
        for (String[] row : rows) {
            sb.append(rowPattern.formatted((Object[]) row));
        }

        return sb.toString();
    }

    /// Create a divider line of character
    public static String divider(char ch) {
        return String.valueOf(ch).repeat(terminalWidth());
    }

    /// Create a divider line of straight line
    public static String divider() { return divider('─'); }

    public static String header(String fmt, Object... args) {
        String result = "%n" + cyan(divider('=') + "%n" + fmt + "%n" + divider('='));
        return result.formatted(args);
    }
}