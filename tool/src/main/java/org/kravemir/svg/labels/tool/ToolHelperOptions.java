package org.kravemir.svg.labels.tool;

import picocli.CommandLine.Option;

public class ToolHelperOptions {

    @Option(
            names = { "-h", "--help" }, usageHelp = true,
            description = "display a help message"
    )
    private boolean helpRequested = false;

    @Option(
            names = {"--version"}, versionHelp = true,
            description = "display version info"
    )
    boolean versionInfoRequested;
}
