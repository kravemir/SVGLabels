import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.kravemir.svg.labels.InstanceRenderer;
import org.kravemir.svg.labels.InstanceRendererImpl;
import org.kravemir.svg.labels.LabelTemplateDescriptor;
import org.kravemir.svg.labels.RenderingUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestTemplateDescriptionJSON {

    private XPath xpath;
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        xpath = XPathFactory.newInstance().newXPath();
        mapper = new ObjectMapper();
    }

    @Test
    public void testInstanceContentReplacement() throws IOException, XPathExpressionException {
        Map<String,String> values = new HashMap<>();
        values.put("name", "JUnit test");
        values.put("description", "Test replacement of texts");
        values.put("date", "13. 05. 2017");

        LabelTemplateDescriptor descriptor = mapper.readValue(
                IOUtils.toString(getClass().getResource("/template01.svg-labels.json")),
                LabelTemplateDescriptor.class
        );

        InstanceRenderer renderer = new InstanceRendererImpl();
        String renderedInstance = renderer.render(
                IOUtils.toString(getClass().getResource("/template01.svg")),
                descriptor,
                values
        );

        SVGDocument instanceDocument = RenderingUtils.parseSVG(renderedInstance);

        System.out.println(renderedInstance);

        assertEquals(1, getCount(instanceDocument, "//*[@id='nameText']/*[1][text()='JUnit test']"));
        assertEquals(1, getCount(instanceDocument, "//*[@id='nameText']/*[2][not(text())]"));
        assertEquals(2, getCount(instanceDocument, "//*[@id='nameText']/*"));
        assertEquals(1, getCount(instanceDocument, "//*[@id='text4540']/*[text()='Test replacement of texts']"));
        assertEquals(1, getCount(instanceDocument, "//*[@id='text4544']/*[text()='13. 05. 2017']"));
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

        InstanceRenderer renderer = new InstanceRendererImpl();
        String renderedInstance = renderer.render(
                IOUtils.toString(getClass().getResource("/template01.svg")),
                descriptor,
                values
        );

        SVGDocument instanceDocument = RenderingUtils.parseSVG(renderedInstance);

        System.out.println(renderedInstance);

        assertEquals(1, getCount(instanceDocument, "//*[@id='nameText']/*[1][text()='Line no. 01']"));
        assertEquals(1, getCount(instanceDocument, "//*[@id='nameText']/*[2][text()='.. line no 02 ..']"));
        assertEquals(2, getCount(instanceDocument, "//*[@id='nameText']/*"));
        assertEquals(1, getCount(instanceDocument, "//*[@id='text4540']/*[text()='Test replacement of texts']"));
        assertEquals(1, getCount(instanceDocument, "//*[@id='text4544']/*[text()='13. 05. 2017']"));
    }

    private int getCount(Document doc, String expression) throws XPathExpressionException {
        return ((NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET)).getLength();
    }
}
