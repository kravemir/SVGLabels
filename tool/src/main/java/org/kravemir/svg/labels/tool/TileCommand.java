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
import org.kravemir.svg.labels.tool.model.ReferringLabelGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Command(
        name = "tile", description = "Tile labels"
)
public class TileCommand extends AbstractCommand {

    private static final TypeReference<HashMap<String,Object>> HASH_MAP_TYPE_REFERENCE = new TypeReference<HashMap<String,Object>>() {};

    @Mixin
    private PaperOptions paperOptions;

    @Option(
            names = "--instance-definitions-location", paramLabel = "FOLDER",
            description = "Path to folder containing JSON files for instances"
    )
    private Path instanceDefinitionsLocation;

    @Option(
            names = "--instance-json", paramLabel = "FILE",
            description = "Path to JSON file containing values for single instance"
    )
    private File instanceJsonFile;

    @Option(
            names = "--instances-json", paramLabel = "FILE",
            description = "Path to JSON file containing array of instances"
    )
    private File instancesJsonFile;

    @Option(
            names = "--template-descriptor", paramLabel = "FILE",
            description = "Path to JSON file containing descriptor of template"
    )
    private File templateDescriptorFile;

    @Parameters(
            index = "0", paramLabel = "SOURCE",
            description = "Path to SVG file containing a label"
    )
    private File source;

    @Parameters(
            index = "1", paramLabel = "TARGET",
            description = "Path to SVG file which should be generated"
    )
    private File target;


    private final TileRenderer renderer;
    private final ObjectMapper mapper;

    public TileCommand() {
        renderer = new TileRendererImpl();

        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }


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
        LabelTemplateDescriptor descriptor = mapper.readValue(
                FileUtils.readFileToString(getDescriptorFile()),
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

    private File getDescriptorFile() {
        if(templateDescriptorFile != null) {
            return templateDescriptorFile;
        } else {
            Path sourcePath = source.toPath();
            return sourcePath.resolveSibling(sourcePath.getFileName() + "-labels.json").toFile();
        }
    }

    private String renderInstances(String templateOrImage) throws IOException {
        LabelTemplateDescriptor descriptor = mapper.readValue(
                FileUtils.readFileToString(getDescriptorFile()),
                LabelTemplateDescriptor.class
        );

        ReferringLabelGroup.Instance[] instances = mapper.readValue(
                FileUtils.readFileToString(instancesJsonFile),
                ReferringLabelGroup.Instance[].class
        );

        List<String> result = renderer.render(
                paperOptions.buildPaper(),
                Collections.singletonList(
                        LabelGroup.builder()
                                .template(templateOrImage)
                                .templateDescriptor(descriptor)
                                .instances(
                                        Arrays.stream(instances)
                                                .map(this::mapInstance)
                                                .collect(Collectors.toList())
                                )
                                .build()
                ),
                DocumentRenderOptions.builder().build()
        );

        return result.get(0);
    }

    private LabelGroup.Instance mapInstance(ReferringLabelGroup.Instance instance) {
        LabelGroup.Instance.Builder builder = LabelGroup.Instance.builder();

        if(instance.instanceContent().isPresent() && instance.instanceContentRef().isPresent()) {
            // TODO: cleanup / think about this, override?
            throw new RuntimeException("Both ref and content are present");
        } else if (instance.instanceContent().isPresent()) {
            builder.instanceContent(instance.instanceContent().get());
        } else if (instance.instanceContentRef().isPresent()) {
            builder.instanceContent(loadInstanceContent(instance.instanceContentRef().get()));
        } else {
            // TODO: cleanup / think about this, not content
            throw new RuntimeException("None of ref and content are present");
        }

        if (instance.shouldFillPage()) {
            builder.fillPage();
        } else {
            builder.count(instance.count());
        }

        return builder.build();
    }

    private Map<String, String> loadInstanceContent(String name) {

        try {
            Map<String, String> values = mapper.readValue(
                    FileUtils.readFileToString(
                            instanceDefinitionsLocation.resolve(name + ".json").toFile()
                    ),
                    HASH_MAP_TYPE_REFERENCE
            );

            return values;
        } catch (IOException e) {
            // TODO: error handling
            throw new RuntimeException(e);
        }
    }
}
