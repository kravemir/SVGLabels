package org.kravemir.svg.labels;

import com.fasterxml.jackson.databind.ObjectMapper;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kravemir.svg.labels.model.TiledPaper;
import org.kravemir.svg.labels.utils.RenderingUtils;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

import static org.junit.Assert.assertThat;
import static org.kravemir.svg.labels.matcher.XPathMatcher.matchesXPath;

@RunWith(JUnitParamsRunner.class)
public class TileRendererImplTest {

    private ObjectMapper mapper;
    private TileRendererImpl renderer;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        renderer = new TileRendererImpl();
    }

    @Test
    @Parameters
    public void testSimpleFullPageInstancing(int rows, int columns) throws IOException, XPathExpressionException {
        int expectedCount = rows * columns;

        TiledPaper paper = createPaper(rows, columns);

        String renderedInstance = renderer.render(
                paper,
                IOUtils.toString(getClass().getResource("/template01.svg"))
        );

        Document instanceDocument = RenderingUtils.parseSVG(renderedInstance);

        System.out.println(renderedInstance);

        assertThat(instanceDocument, matchesXPath( expectedCount * 1, "//*[@id='nameText']/*[1][text()='Multiline']"));
        assertThat(instanceDocument, matchesXPath( expectedCount * 1, "//*[@id='nameText']/*[2][text()='name']"));
        assertThat(instanceDocument, matchesXPath( expectedCount * 2, "//*[@id='nameText']/*"));
    }

    private Object[] parametersForTestSimpleFullPageInstancing() {
        return new Object[][] {
                {1, 1},
                {2, 1},
                {1, 2},
                {9, 9}
        };
    }

    private TiledPaper createPaper(int rows, int column) {
        final int instanceWidth = 80;
        final int instanceHeight = 80;

        return TiledPaper.builder()
                .setPaperSize(20 + instanceWidth * column, 20 + instanceHeight * rows)
                .setLabelSize(instanceWidth, 80)
                .setLabelOffset(10, 10)
                .setLabelDelta(0, 0)
                .build();
    }
}
