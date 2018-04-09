package org.kravemir.svg.labels.tool;

import org.apache.commons.io.IOUtils;
import org.kravemir.svg.labels.api.TileRenderer;
import org.kravemir.svg.labels.api.TiledPaper;
import org.kravemir.svg.labels.impl.TileRendererImpl;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.*;

@Command(name = "tile", description = "Tile labels")
public class TileCommand implements Runnable {

    @Option(arity = "2", names = "--paper-size")
    double[] paperSize;

    @Option(arity = "2", names = "--label-offset")
    double[] labelOffset;

    @Option(arity = "2", names = "--label-size")
    double[] labelSize;

    @CommandLine.Parameters(index = "0", paramLabel = "SOURCE")
    String source;

    @CommandLine.Parameters(index = "1", paramLabel = "TARGET")
    String target;

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
