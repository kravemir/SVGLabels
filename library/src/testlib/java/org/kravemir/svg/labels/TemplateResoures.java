package org.kravemir.svg.labels;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matcher;
import org.kravemir.svg.labels.model.LabelTemplateDescriptor;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.kravemir.svg.labels.matcher.NodesMatchingXPath.nodesMatchingXPath;

public final class TemplateResoures {

    public static final Map<String,String> DATA_01 = MapUtils.putAll(new HashMap<>(), new String[][] {
            {"name", "JUnit test"},
            {"description", "Test replacement of texts"},
            {"date", "13. 05. 2017"}
    });

    public static final Map<String,String> DATA_02 = MapUtils.putAll(new HashMap<>(), new String[][]{
            {"name", "Line no. 01\n.. line no 02 .."},
            {"description", "Test replacement of texts"},
            {"date", "13. 05. 2017"}
    });

    public static final Supplier<String> TEMPLATE_01 = getTemplateFromResource("/template01.svg");
    public static final Supplier<String> TEMPLATE_02 = getTemplateFromResource("/template02.svg");

    public static final Supplier<LabelTemplateDescriptor> TEMPLATE_01_DESCRIPTOR = getDescriptorFromResource("/template01.svg-labels.json");
    public static final Supplier<LabelTemplateDescriptor> TEMPLATE_02_DESCRIPTOR = getDescriptorFromResource("/template02.svg-labels.json");

    public static final Matcher<Node> TEMPLATE_01_MATCHER = allOf(
            nodesMatchingXPath( ".//*[@id='nameText']/*[1][text()='Multiline']", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='nameText']/*[2][text()='name']", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='nameText']/*", hasSize(2))
    );
    public static final Matcher<Node> TEMPLATE_01_DATA_01_MATCHER = allOf(
            nodesMatchingXPath( ".//*[@id='nameText']/*[1][text()='JUnit test']", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='nameText']/*[2][not(text())]", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='nameText']/*", hasSize(2)),
            nodesMatchingXPath( ".//*[@id='text4540']/*[text()='Test replacement of texts']", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='text4544']/*[text()='13. 05. 2017']", hasSize(1))
    );
    public static final Matcher<Node> TEMPLATE_01_DATA_02_MATCHER = allOf(
            nodesMatchingXPath(".//*[@id='nameText']/*[1][text()='Line no. 01']", hasSize(1)),
            nodesMatchingXPath(".//*[@id='nameText']/*[2][text()='.. line no 02 ..']", hasSize(1)),
            nodesMatchingXPath(".//*[@id='nameText']/*", hasSize(2)),
            nodesMatchingXPath(".//*[@id='text4540']/*[text()='Test replacement of texts']", hasSize(1)),
            nodesMatchingXPath(".//*[@id='text4544']/*[text()='13. 05. 2017']", hasSize(1))
    );
    public static final Matcher<Node> TEMPLATE_02_MATCHER = allOf(
            nodesMatchingXPath( ".//*[@id='text-large']/*[1][text()='Large font']", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='text-large']/*[2][text()='TEXT']", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='text-large']/*", hasSize(2))
    );

    private static Supplier<String> getTemplateFromResource(String resource) {
        return () -> {
            try {
                return  IOUtils.toString(TemplateResoures.class.getResource(resource));
            } catch (IOException e) {
                throw new RuntimeException("This should not happen!!!", e);
            }
        };
    }

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static Supplier<LabelTemplateDescriptor> getDescriptorFromResource(String resource) {
        return () -> {
            try {
                return OBJECT_MAPPER.readValue(
                        IOUtils.toString(TemplateResoures.class.getResource(resource)),
                        LabelTemplateDescriptor.class
                );
            } catch (IOException e) {
                throw new RuntimeException("This should not happen!!!", e);
            }
        };
    }
}
