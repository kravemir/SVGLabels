package org.kravemir.svg.labels.tool;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.kravemir.svg.labels.*;
import org.kravemir.svg.labels.model.LabelTemplateDescriptor;
import org.kravemir.svg.labels.model.TiledPaper;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

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
            arity = "2", names = "--label-delta", paramLabel = "mm",
            description = "X and Y delta between labels in mm, ie. 5 5"
    )
    private double[] labelDelta;

    @Option(
            arity = "2", names = "--label-size", paramLabel = "mm",
            description = "Width and height of label in mm, ie. "
    )
    private double[] labelSize;

    @Option(
            names = "--instance-json"
    )
    private File instanceJsonFile;

    @Parameters(
            index = "0", paramLabel = "SOURCE",
            description = "Path of a SVG file containing a label"
    )
    private File source;

    @Parameters(
            index = "1", paramLabel = "TARGET",
            description = "Path of a SVG file which should be generated"
    )
    private File target;


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
            double labelDeltaX = labelDelta[0];
            double labelDeltaY = labelDelta[1];
            double labelWidth = labelSize[0];
            double labelHeight = labelSize[1];

            String svg = FileUtils.readFileToString(source);

            if(instanceJsonFile != null) {
                Path sourcePath = source.toPath();

                ObjectMapper mapper = new ObjectMapper();

                LabelTemplateDescriptor descriptor = mapper.readValue(
                        FileUtils.readFileToString(sourcePath.resolveSibling(sourcePath.getFileName() + "-labels.json").toFile()),
                        LabelTemplateDescriptor.class
                );

                TypeReference<HashMap<String,Object>> hashMapTypeReference
                        = new TypeReference<HashMap<String,Object>>() {};
                HashMap<String,String> values = mapper.readValue(
                        FileUtils.readFileToString(instanceJsonFile),
                        hashMapTypeReference
                );

                InstanceRenderer renderer = new InstanceRendererImpl();
                svg = renderer.render(svg, descriptor, values);
            }

            TileRenderer renderer = new TileRendererImpl();
            String result = renderer.render(
                    TiledPaper.builder()
                            .setPaperSize(paperWidth, paperHeight)
                            .setLabelOffset(labelOffsetX, labelOffsetY)
                            .setLabelSize(labelWidth, labelHeight)
                            .setLabelDelta(labelDeltaX, labelDeltaY)
                            .build(),
                    svg
            );
            FileUtils.writeStringToFile(
                    target,
                    result
            );
        } catch (IOException | XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }
}
