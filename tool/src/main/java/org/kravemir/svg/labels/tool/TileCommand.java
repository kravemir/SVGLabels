package org.kravemir.svg.labels.tool;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.kravemir.svg.labels.TileRenderer;
import org.kravemir.svg.labels.TileRendererImpl;
import org.kravemir.svg.labels.model.DocumentRenderOptions;
import org.kravemir.svg.labels.model.LabelGroup;
import org.kravemir.svg.labels.model.LabelTemplateDescriptor;
import org.kravemir.svg.labels.tool.common.AbstractCommand;
import org.kravemir.svg.labels.tool.common.PaperOptions;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Command(
        name = "tile", description = "Tile labels"
)
public class TileCommand extends AbstractCommand {

    private static final TypeReference<HashMap<String,Object>> HASH_MAP_TYPE_REFERENCE = new TypeReference<HashMap<String,Object>>() {};

    @Mixin
    private PaperOptions paperOptions;

    @Option(
            names = "--instance-json"
    )
    private File instanceJsonFile;

    @Option(
            names = "--instances-json"
    )
    private File instancesJsonFile;

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


    private final TileRenderer renderer = new TileRendererImpl();


    public void run() {
        try {
            String svg = FileUtils.readFileToString(source);
            String result;

            if (instancesJsonFile != null) {
                result = renderInstances(svg);
            } else if (instanceJsonFile != null) {
                result = renderInstance(svg);
            } else {
                result = renderer.renderSinglePageWithLabel(paperOptions.buildPaper(), svg);
            }

            FileUtils.writeStringToFile(target, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String renderInstance(String templateOrImage) throws IOException {
        Path sourcePath = source.toPath();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        LabelTemplateDescriptor descriptor = mapper.readValue(
                FileUtils.readFileToString(sourcePath.resolveSibling(sourcePath.getFileName() + "-labels.json").toFile()),
                LabelTemplateDescriptor.class
        );

        HashMap<String,String> values = mapper.readValue(
                FileUtils.readFileToString(instanceJsonFile),
                HASH_MAP_TYPE_REFERENCE
        );

        List<String> result = renderer.render(
                paperOptions.buildPaper(),
                Collections.singletonList(
                        LabelGroup.builder()
                                .template(templateOrImage)
                                .templateDescriptor(descriptor)
                                .instances(LabelGroup.Instance.builder().fillPage().instanceContent(values).build())
                                .build()
                ),
                DocumentRenderOptions.builder().build()
        );

        return result.get(0);
    }

    private String renderInstances(String templateOrImage) throws IOException {
        Path sourcePath = source.toPath();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        LabelTemplateDescriptor descriptor = mapper.readValue(
                FileUtils.readFileToString(sourcePath.resolveSibling(sourcePath.getFileName() + "-labels.json").toFile()),
                LabelTemplateDescriptor.class
        );

        LabelGroup.Instance[] instances = mapper.readValue(
                FileUtils.readFileToString(instancesJsonFile),
                LabelGroup.Instance[].class
        );

        List<String> result = renderer.render(
                paperOptions.buildPaper(),
                Collections.singletonList(
                        LabelGroup.builder()
                                .template(templateOrImage)
                                .templateDescriptor(descriptor)
                                .instances(instances)
                                .build()
                ),
                DocumentRenderOptions.builder().build()
        );

        return result.get(0);
    }
}
