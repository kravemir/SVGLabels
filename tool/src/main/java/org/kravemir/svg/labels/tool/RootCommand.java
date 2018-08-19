package org.kravemir.svg.labels.tool;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "svg-labels",
        versionProvider = VersionProvider.class
)
public class RootCommand implements Runnable {

    @Option(
            names = {"-v", "--version"}, versionHelp = true,
            description = "display version info"
    )
    boolean versionInfoRequested;

    @Override
    public void run() {
        System.err.println("Sub-command is required, see: svg-labels -h");
        System.exit(1);
    }
}
