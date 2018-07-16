package org.kravemir.svg.labels.tool;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.kravemir.svg.labels.utils.RenderingUtils;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
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
    public void testRenderWithoutInstance() throws URISyntaxException, IOException {
        ToolRunner.main( new String[]{
                "--paper-size", "210", "297",
                "--label-offset", "0", "0",
                "--label-size", "65", "26.5",
                "--label-delta", "0", "0",
                Paths.get(getClass().getResource("/template01.svg").toURI()).toFile().getAbsolutePath(),
                outputFile.getAbsolutePath()
        });
        System.out.println(FileUtils.readFileToString(outputFile));
    }

    @Test
    public void testRenderWithInstance() throws URISyntaxException, IOException {
        ToolRunner.main( new String[]{
                "--paper-size", "210", "297",
                "--label-offset", "0", "0",
                "--label-size", "65", "26.5",
                "--label-delta", "0", "0",
                "--instance-json",
                Paths.get(getClass().getResource("/test-instance.json").toURI()).toFile().getAbsolutePath(),
                Paths.get(getClass().getResource("/template01.svg").toURI()).toFile().getAbsolutePath(),
                outputFile.getAbsolutePath()
        });
        System.out.println(FileUtils.readFileToString(outputFile));
    }

    @Test
    public void testRenderWithInstances() throws URISyntaxException, IOException {
        /* TODO: there's an error, if page isn't fully filled, therefore count 200 last item was added,.. fix it!  */

        ToolRunner.main( new String[]{
                "tile",
                "--paper-size", "210", "297",
                "--label-offset", "0", "0",
                "--label-size", "65", "26.5",
                "--label-delta", "0", "0",
                "--instances-json",
                Paths.get(getClass().getResource("/test-instances.json").toURI()).toFile().getAbsolutePath(),
                Paths.get(getClass().getResource("/template01.svg").toURI()).toFile().getAbsolutePath(),
                outputFile.getAbsolutePath()
        });
        System.out.println(FileUtils.readFileToString(outputFile));

        Document instanceDocument = RenderingUtils.parseSVG(FileUtils.readFileToString(outputFile));
        assertThat(instanceDocument, nodesMatchingXPath("//*", hasSize(greaterThan(1))));
    }

}
