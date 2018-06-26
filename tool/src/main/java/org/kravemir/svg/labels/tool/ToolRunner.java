package org.kravemir.svg.labels.tool;

import picocli.CommandLine;

import java.util.List;

public class ToolRunner {
    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new TileCommand());
        commandLine.addMixin("helperOptions", new ToolHelperOptions());

        commandLine.parseWithHandlers(
                new CommandLine.RunLast().useOut(System.out).useAnsi(CommandLine.Help.Ansi.AUTO),
                new CommandLine.DefaultExceptionHandler<List<Object>>().useErr(System.err).useAnsi(CommandLine.Help.Ansi.AUTO),
                args
        );
    }
}
