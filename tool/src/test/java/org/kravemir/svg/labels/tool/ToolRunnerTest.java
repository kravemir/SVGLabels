package org.kravemir.svg.labels.tool;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.kravemir.svg.labels.utils.RenderingUtils;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

    // TODO: refactor to proper place
    // TODO: think about descritor path...
    private File getTemplateFromResource(String name, boolean withDescriptorJSON) throws IOException {
        File copyFile = folder.newFile();
        OutputStream copyFileStream = new FileOutputStream(copyFile);
        IOUtils.copy(
                getClass().getResource(name).openStream(),
                copyFileStream
        );
        copyFileStream.close();

        if(withDescriptorJSON) {
            File copyFileDescriptor = folder.newFile(copyFile.getName() + "-labels.json");
            OutputStream copyFileDescriptorStream = new FileOutputStream(copyFileDescriptor);
            IOUtils.copy(
                    getClass().getResource(name + "-labels.json").openStream(),
                    copyFileDescriptorStream
            );
            copyFileDescriptorStream.close();
        }

        return copyFile;
    }

    @Test
    public void testRenderWithoutInstance() throws IOException {
        ToolRunner.main( new String[]{
                "--paper-size", "210", "297",
                "--label-offset", "0", "0",
                "--label-size", "65", "26.5",
                "--label-delta", "0", "0",
                getTemplateFromResource("/template01.svg", true).getAbsolutePath(),
                outputFile.getAbsolutePath()
        });
        System.out.println(FileUtils.readFileToString(outputFile));
    }

    @Test
    public void testRenderWithInstance() throws IOException {
        ToolRunner.main( new String[]{
                "--paper-size", "210", "297",
                "--label-offset", "0", "0",
                "--label-size", "65", "26.5",
                "--label-delta", "0", "0",
                "--instance-json",
                getTemplateFromResource("/test-instance.json", false).getAbsolutePath(),
                getTemplateFromResource("/template01.svg", true).getAbsolutePath(),
                outputFile.getAbsolutePath()
        });
        System.out.println(FileUtils.readFileToString(outputFile));
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
                getTemplateFromResource("/test-instances.json", false).getAbsolutePath(),
                getTemplateFromResource("/template01.svg", true).getAbsolutePath(),
                outputFile.getAbsolutePath()
        });
        System.out.println(FileUtils.readFileToString(outputFile));

        Document instanceDocument = RenderingUtils.parseSVG(FileUtils.readFileToString(outputFile));
        assertThat(instanceDocument, nodesMatchingXPath("//*", hasSize(greaterThan(1))));
    }

}
