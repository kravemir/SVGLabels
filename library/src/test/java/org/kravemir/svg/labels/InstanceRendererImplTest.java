package org.kravemir.svg.labels;

import com.fasterxml.jackson.databind.ObjectMapper;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kravemir.svg.labels.model.LabelTemplateDescriptor;
import org.kravemir.svg.labels.utils.RenderingUtils;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;
import static org.kravemir.svg.labels.matcher.XPathMatcher.matchesXPath;

@RunWith(JUnitParamsRunner.class)
public class InstanceRendererImplTest {

    private ObjectMapper mapper;
    private InstanceRendererImpl renderer;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        renderer = new InstanceRendererImpl();
    }

    @Test
    public void testInstanceContentReplacement() throws IOException, XPathExpressionException {

        LabelTemplateDescriptor descriptor = mapper.readValue(
                IOUtils.toString(getClass().getResource("/template01.svg-labels.json")),
                LabelTemplateDescriptor.class
        );

        String renderedInstance = renderer.render(
                IOUtils.toString(getClass().getResource("/template01.svg")),
                descriptor,
                TemplateResoures.DATA_01
        );

        Document instanceDocument = RenderingUtils.parseSVG(renderedInstance);

        System.out.println(renderedInstance);

        assertThat(instanceDocument, TemplateResoures.TEMPLATE_01_DATA_01_MATCHER);
    }

    @Test
    public void testMultilineReplacement() throws IOException, XPathExpressionException {
        Map<String,String> values = new HashMap<>();
        values.put("name", "Line no. 01\n.. line no 02 ..");
        values.put("description", "Test replacement of texts");
        values.put("date", "13. 05. 2017");

        LabelTemplateDescriptor descriptor = mapper.readValue(
                IOUtils.toString(getClass().getResource("/template01.svg-labels.json")),
                LabelTemplateDescriptor.class
        );

        String renderedInstance = renderer.render(
                IOUtils.toString(getClass().getResource("/template01.svg")),
                descriptor,
                values
        );

        Document instanceDocument = RenderingUtils.parseSVG(renderedInstance);

        System.out.println(renderedInstance);

        assertThat(instanceDocument, matchesXPath( 1, "//*[@id='nameText']/*[1][text()='Line no. 01']"));
        assertThat(instanceDocument, matchesXPath( 1, "//*[@id='nameText']/*[2][text()='.. line no 02 ..']"));
        assertThat(instanceDocument, matchesXPath( 2, "//*[@id='nameText']/*"));
        assertThat(instanceDocument, matchesXPath( 1, "//*[@id='text4540']/*[text()='Test replacement of texts']"));
        assertThat(instanceDocument, matchesXPath( 1, "//*[@id='text4544']/*[text()='13. 05. 2017']"));
    }

    @Test
    @Parameters
    public void testMultipleReplacements(String size, Matcher<? super Document> matcher) throws IOException, XPathExpressionException {
        Map<String,String> values = new HashMap<>();
        values.put("text", "Some multi-line\ntext");
        values.put("text_size", size);

        LabelTemplateDescriptor descriptor = mapper.readValue(
                IOUtils.toString(getClass().getResource("/template02.svg-labels.json")),
                LabelTemplateDescriptor.class
        );

        String renderedInstance = renderer.render(
                IOUtils.toString(getClass().getResource("/template02.svg")),
                descriptor,
                values
        );

        Document instanceDocument = RenderingUtils.parseSVG(renderedInstance);

        System.out.println(renderedInstance);

        assertThat(instanceDocument, matcher);
    }

    private static Object[] parametersForTestMultipleReplacements() {
        return new Object[]{
                new Object[] { "unknown", allOf(
                        matchesXPath(1, "//*[@id='text-large']/*[1][not(text())]"),
                        matchesXPath(1, "//*[@id='text-large']/*[2][not(text())]"),
                        matchesXPath(1, "//*[@id='text-medium']/*[1][not(text())]"),
                        matchesXPath(1, "//*[@id='text-medium']/*[2][not(text())]"),
                        matchesXPath(1, "//*[@id='text-small']/*[1][not(text())]"),
                        matchesXPath(1, "//*[@id='text-small']/*[2][not(text())]")
                )},
                new Object[] { "large", allOf(
                        matchesXPath(1, "//*[@id='text-large']/*[1][text()='Some multi-line']"),
                        matchesXPath(1, "//*[@id='text-large']/*[2][text()='text']"),
                        matchesXPath(1, "//*[@id='text-medium']/*[1][not(text())]"),
                        matchesXPath(1, "//*[@id='text-medium']/*[2][not(text())]"),
                        matchesXPath(1, "//*[@id='text-small']/*[1][not(text())]"),
                        matchesXPath(1, "//*[@id='text-small']/*[2][not(text())]")
                )},
                new Object[] { "medium", allOf(
                        matchesXPath(1, "//*[@id='text-large']/*[1][not(text())]"),
                        matchesXPath(1, "//*[@id='text-large']/*[2][not(text())]"),
                        matchesXPath(1, "//*[@id='text-medium']/*[1][text()='Some multi-line']"),
                        matchesXPath(1, "//*[@id='text-medium']/*[2][text()='text']"),
                        matchesXPath(1, "//*[@id='text-small']/*[1][not(text())]"),
                        matchesXPath(1, "//*[@id='text-small']/*[2][not(text())]")
                )},
                new Object[] { "small", allOf(
                        matchesXPath(1, "//*[@id='text-large']/*[1][not(text())]"),
                        matchesXPath(1, "//*[@id='text-large']/*[2][not(text())]"),
                        matchesXPath(1, "//*[@id='text-medium']/*[1][not(text())]"),
                        matchesXPath(1, "//*[@id='text-medium']/*[2][not(text())]"),
                        matchesXPath(1, "//*[@id='text-small']/*[1][text()='Some multi-line']"),
                        matchesXPath(1, "//*[@id='text-small']/*[2][text()='text']")
                )},
        };
    }
}
