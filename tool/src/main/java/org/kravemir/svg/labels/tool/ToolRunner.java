package org.kravemir.svg.labels.tool;

import picocli.CommandLine;

import java.util.List;

public class ToolRunner {
    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new TileCommand());
        commandLine.addMixin("helperOptions", new ToolHelperOptions());

        commandLine.parseWithHandler(
                new CommandLine.RunLast().useOut(System.out).useAnsi(CommandLine.Help.Ansi.AUTO),
                args
        );
    }
}
