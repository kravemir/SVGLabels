package org.kravemir.svg.labels.tool;

import org.apache.commons.io.IOUtils;
import org.kravemir.svg.labels.TileRenderer;
import org.kravemir.svg.labels.TileRendererImpl;
import org.kravemir.svg.labels.TiledPaper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Command(
        name = "tile", description = "Tile labels",
        versionProvider = VersionProvider.class
)
public class TileCommand implements Runnable {

    @Option(
            arity = "2", names = "--paper-size", paramLabel = "mm",
            description = "Width and height of the paper in mm, ie. 210 297 for A4 paper portrait"
    )
    private double[] paperSize;

    @Option(
            arity = "2", names = "--label-offset", paramLabel = "mm",
            description = "X and Y offset of the first label in mm, ie. 5 5"
    )
    private double[] labelOffset;

    @Option(
            arity = "2", names = "--label-size", paramLabel = "mm",
            description = "Width and height of label in mm, ie. "
    )
    private double[] labelSize;

    @Parameters(
            index = "0", paramLabel = "SOURCE",
            description = "Path of a SVG file containing a label"
    )
    private String source;

    @Parameters(
            index = "1", paramLabel = "TARGET",
            description = "Path of a SVG file which should be generated"
    )
    private String target;


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

    public void run() {
        try {
            double paperWidth = paperSize[0];
            double paperHeight = paperSize[1];
            double labelOffsetX = labelOffset[0];
            double labelOffsetY = labelOffset[1];
            double labelWidth = labelSize[0];
            double labelHeight = labelSize[1];

            System.out.println(String.format("Source:       %s", source));
            System.out.println(String.format("Target:       %s", target));
            System.out.println(String.format("Paper size:   %.2f x %.2f [mm]", paperWidth, paperHeight));
            System.out.println(String.format("Label offset: %.2f + %.2f [mm]", labelOffsetX, labelOffsetY));
            System.out.println(String.format("Label size:   %.2f x %.2f [mm]", labelWidth, labelHeight));

            TileRenderer renderer = new TileRendererImpl();
            String result = renderer.render(
                    TiledPaper.builder()
                            .withPaperSize(paperWidth, paperHeight)
                            .withLabelOffset(labelOffsetX, labelOffsetY)
                            .withLabelSize(labelWidth, labelHeight)
                            .build(),
                    IOUtils.toString(new FileInputStream(source))
            );
            IOUtils.write(
                    result,
                    new FileOutputStream(new File(target))
            );
        } catch (IOException ignored) {
        }
    }
}
