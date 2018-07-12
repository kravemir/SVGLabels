package org.kravemir.svg.labels;

import com.fasterxml.jackson.databind.ObjectMapper;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kravemir.svg.labels.model.DocumentRenderOptions;
import org.kravemir.svg.labels.model.LabelGroup;
import org.kravemir.svg.labels.model.LabelTemplateDescriptor;
import org.kravemir.svg.labels.model.TiledPaper;
import org.kravemir.svg.labels.utils.RenderingUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.kravemir.svg.labels.TemplateResoures.*;
import static org.kravemir.svg.labels.matcher.NodesMatchingXPath.nodesMatchingXPath;

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

        assertThat(instanceDocument, nodesMatchingXPath("/*/*", Matchers.<List<Node>>allOf(
                hasSize(expectedCount),
                everyItem( TEMPLATE_01_MATCHER )
        )));
    }

    private Object[] parametersForTestSimpleFullPageInstancing() {
        return new Object[][] {
                {1, 1},
                {2, 1},
                {1, 2},
                {9, 9}
        };
    }

    @Test
    public void testMultipleLabels() throws IOException, XPathExpressionException {
        int rows = 2, columns = 4;
        int expectedCount = rows * columns;

        TiledPaper paper = createPaper(rows, columns);

        List<String> renderedInstance = renderer.render(
                paper,
                Arrays.asList(
                        LabelGroup.builder()
                                .setTemplate(IOUtils.toString(getClass().getResource("/template01.svg")))
                                .setInstances(LabelGroup.Instance.builder().setCount(1).build())
                                .build(),
                        LabelGroup.builder()
                                .setTemplate(IOUtils.toString(getClass().getResource("/template02.svg")))
                                .setInstances(LabelGroup.Instance.builder().fillPage().build())
                                .build()

                ),
                DocumentRenderOptions.builder().build()
        );

        Document instanceDocument = RenderingUtils.parseSVG(renderedInstance.get(0));

        System.out.println(renderedInstance);

        assertThat(instanceDocument, nodesMatchingXPath("/*/*", Matchers.<List<Node>>allOf(
                hasSize(expectedCount),
                contains(
                        Stream.concat(
                                repeat(1, allOf(TEMPLATE_01_MATCHER, not(TEMPLATE_02_MATCHER))),
                                repeat(expectedCount - 1, allOf(TEMPLATE_02_MATCHER, not(TEMPLATE_01_MATCHER)))
                        ).collect(Collectors.toList())
                )
        )));
    }

    @Test
    public void testMultipleLabelsWithReplacement() throws IOException, XPathExpressionException {
        int rows = 2, columns = 4;
        int expectedCount = rows * columns;

        TiledPaper paper = createPaper(rows, columns);

        LabelTemplateDescriptor descriptor = mapper.readValue(
                IOUtils.toString(getClass().getResource("/template01.svg-labels.json")),
                LabelTemplateDescriptor.class
        );

        List<String> renderedInstance = renderer.render(
                paper,
                Arrays.asList(
                        LabelGroup.builder()
                                .setTemplate(IOUtils.toString(getClass().getResource("/template01.svg")))
                                .setInstances(LabelGroup.Instance.builder().setCount(1).build())
                                .build(),
                        LabelGroup.builder()
                                .setTemplate(IOUtils.toString(getClass().getResource("/template01.svg")))
                                .setTemplateDescriptor(descriptor)
                                .setInstances(LabelGroup.Instance.builder().setInstanceContent(DATA_01).setCount(1).build())
                                .build(),
                        LabelGroup.builder()
                                .setTemplate(IOUtils.toString(getClass().getResource("/template01.svg")))
                                .setTemplateDescriptor(descriptor)
                                .setInstances(LabelGroup.Instance.builder().setInstanceContent(DATA_02).setCount(1).build())
                                .build(),
                        LabelGroup.builder()
                                .setTemplate(IOUtils.toString(getClass().getResource("/template02.svg")))
                                .setInstances(LabelGroup.Instance.builder().fillPage().build())
                                .build()

                ),
                DocumentRenderOptions.builder().build()
        );

        Document instanceDocument = RenderingUtils.parseSVG(renderedInstance.get(0));

        System.out.println(renderedInstance);

        assertThat(instanceDocument, nodesMatchingXPath("/*/*", Matchers.<List<Node>>allOf(
                hasSize(expectedCount),
                containsConcat(
                        repeat(1, allOf(TEMPLATE_01_MATCHER, not(TEMPLATE_02_MATCHER))),
                        repeat(1, allOf(TEMPLATE_01_DATA_01_MATCHER, not(TEMPLATE_01_MATCHER), not(TEMPLATE_02_MATCHER))),
                        repeat(1, allOf(TEMPLATE_01_DATA_02_MATCHER, not(TEMPLATE_01_MATCHER), not(TEMPLATE_02_MATCHER))),
                        repeat(expectedCount - 3, allOf(TEMPLATE_02_MATCHER, not(TEMPLATE_01_MATCHER)))
                )
        )));
    }

    private <T> Matcher<Iterable<? extends T>> containsConcat(Stream<Matcher<? super T>>... matchers) {
        List<Matcher<? super T>> matcherList = Stream
                .of(matchers)
                .flatMap(Function.identity())
                .collect(Collectors.toList());
        return contains(matcherList);
    }

    private <T> Stream<Matcher<? super T>> repeat(int times, Matcher<? super T> element) {
        return IntStream.range(0, times).mapToObj(i -> element);
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
