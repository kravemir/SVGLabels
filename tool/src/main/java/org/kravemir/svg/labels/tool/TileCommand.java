package org.kravemir.svg.labels.tool;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.kravemir.svg.labels.InstanceRenderer;
import org.kravemir.svg.labels.InstanceRendererImpl;
import org.kravemir.svg.labels.TileRenderer;
import org.kravemir.svg.labels.TileRendererImpl;
import org.kravemir.svg.labels.model.LabelTemplateDescriptor;
import org.kravemir.svg.labels.model.TiledPaper;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
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

    @Mixin
    private PaperOptions paperOptions;

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


    public void run() {
        try {
            double paperWidth = paperOptions.getPaperSize()[0];
            double paperHeight = paperOptions.getPaperSize()[1];
            double labelOffsetX = paperOptions.getLabelOffset()[0];
            double labelOffsetY = paperOptions.getLabelOffset()[1];
            double labelDeltaX = paperOptions.getLabelDelta()[0];
            double labelDeltaY = paperOptions.getLabelDelta()[1];
            double labelWidth = paperOptions.getLabelSize()[0];
            double labelHeight = paperOptions.getLabelSize()[1];

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
