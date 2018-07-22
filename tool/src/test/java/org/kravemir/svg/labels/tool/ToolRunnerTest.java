package org.kravemir.svg.labels.tool;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.kravemir.svg.labels.TemplateResoures;
import org.kravemir.svg.labels.utils.RenderingUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.kravemir.svg.labels.TemplateResoures.*;
import static org.kravemir.svg.labels.matcher.NodesMatchingXPath.nodesMatchingXPath;

public class ToolRunnerTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private File outputFile = null;

    @Before
    public void setUp() throws Exception {
        outputFile = folder.newFile("testOutput");
    }

    @Test
    public void testRenderWithoutInstance() throws IOException {
        ToolRunner.main( new String[]{
                "tile",
                "--paper-size", "210", "297",
                "--label-offset", "0", "0",
                "--label-size", "65", "26.5",
                "--label-delta", "0", "0",
                TemplateResoures.TEMPLATE_01.getAsFile(folder::newFile).getAbsolutePath(),
                outputFile.getAbsolutePath()
        });
        System.out.println(FileUtils.readFileToString(outputFile));

        Document instanceDocument = RenderingUtils.parseSVG(FileUtils.readFileToString(outputFile));
        assertThat(instanceDocument, nodesMatchingXPath("/*/*", Matchers.<Collection<Node>>allOf(
                hasSize(33),
                everyItem(allOf(TEMPLATE_01_MATCHER, not(TEMPLATE_01_DATA_01_MATCHER), not(TEMPLATE_02_MATCHER)))
        )));
    }

    @Test
    public void testRenderWithInstance() throws IOException {
        ToolRunner.main( new String[]{
                "tile",
                "--paper-size", "210", "297",
                "--label-offset", "0", "0",
                "--label-size", "65", "26.5",
                "--label-delta", "0", "0",
                "--instance-json",
                TemplateResoures.DATA_01.getAsFile(folder::newFile).getAbsolutePath(),
                "--template-descriptor",
                TemplateResoures.TEMPLATE_01_DESCRIPTOR.getAsFile(folder::newFile).getAbsolutePath(),
                TemplateResoures.TEMPLATE_01.getAsFile(folder::newFile).getAbsolutePath(),
                outputFile.getAbsolutePath()
        });
        System.out.println(FileUtils.readFileToString(outputFile));

        Document instanceDocument = RenderingUtils.parseSVG(FileUtils.readFileToString(outputFile));
        assertThat(instanceDocument, nodesMatchingXPath("/*/*", Matchers.<Collection<Node>>allOf(
                hasSize(33),
                everyItem(allOf(TEMPLATE_01_DATA_01_MATCHER, not(TEMPLATE_01_MATCHER), not(TEMPLATE_02_MATCHER)))
        )));
    }

    @Test
    public void testRenderWithInstances() throws IOException {
        /* TODO: there's an error, if page isn't fully filled, therefore count 200 last item was added,.. fix it!  */

        ToolRunner.main( new String[]{
                "tile",
                "--paper-size", "210", "297",
                "--label-offset", "0", "0",
                "--label-size", "65", "26.5",
                "--label-delta", "0", "0",
                "--instances-json",
                TemplateResoures.INSTANCES_01.getAsFile(folder::newFile).getAbsolutePath(),
                "--template-descriptor",
                TemplateResoures.TEMPLATE_01_DESCRIPTOR.getAsFile(folder::newFile).getAbsolutePath(),
                TemplateResoures.TEMPLATE_01.getAsFile(folder::newFile).getAbsolutePath(),
                outputFile.getAbsolutePath()
        });
        System.out.println(FileUtils.readFileToString(outputFile));

        Document instanceDocument = RenderingUtils.parseSVG(FileUtils.readFileToString(outputFile));
        assertThat(instanceDocument, nodesMatchingXPath("/*/*", Matchers.<Collection<Node>>allOf(
                hasSize(33),
                hasSize(33),
                Matchers.contains(
                        TEMPLATE_01_INSTANCES_01_MATCHER_LIST.subList(0, 33)
                )
        )));
    }
}
