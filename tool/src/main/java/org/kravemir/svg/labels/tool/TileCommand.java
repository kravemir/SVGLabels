package org.kravemir.svg.labels.tool;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.kravemir.svg.labels.InstanceRenderer;
import org.kravemir.svg.labels.InstanceRendererImpl;
import org.kravemir.svg.labels.TileRenderer;
import org.kravemir.svg.labels.TileRendererImpl;
import org.kravemir.svg.labels.model.LabelTemplateDescriptor;
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

    private static final TypeReference<HashMap<String,Object>> HASH_MAP_TYPE_REFERENCE = new TypeReference<HashMap<String,Object>>() {};

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
            String svg = FileUtils.readFileToString(source);
            svg = processSVGTemplate(svg);

            TileRenderer renderer = new TileRendererImpl();
            String result = renderer.render(paperOptions.buildPaper(), svg);
            FileUtils.writeStringToFile(target, result);
        } catch (IOException | XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    private String processSVGTemplate(String templateOrImage) throws IOException, XPathExpressionException {
        if(instanceJsonFile == null) {
            return templateOrImage;
        }

        Path sourcePath = source.toPath();

        ObjectMapper mapper = new ObjectMapper();

        LabelTemplateDescriptor descriptor = mapper.readValue(
                FileUtils.readFileToString(sourcePath.resolveSibling(sourcePath.getFileName() + "-labels.json").toFile()),
                LabelTemplateDescriptor.class
        );

        HashMap<String,String> values = mapper.readValue(
                FileUtils.readFileToString(instanceJsonFile),
                HASH_MAP_TYPE_REFERENCE
        );

        InstanceRenderer renderer = new InstanceRendererImpl();
        return renderer.render(templateOrImage, descriptor, values);
    }
}
