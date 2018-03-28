package org.kravemir.svg.tiler.tool;

import picocli.CommandLine;

public class ToolRunner {
    public static void main(String[] args) {
        CommandLine.run(new TileCommand(), System.out, args);
    }
}
