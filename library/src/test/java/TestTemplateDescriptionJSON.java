import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.kravemir.svg.labels.InstanceRenderer;
import org.kravemir.svg.labels.LabelTemplateDescriptor;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestTemplateDescriptionJSON {

    @Test
    public void testInstanceContentReplacement() throws IOException, XPathExpressionException {
        Map<String,String> values = new HashMap<>();
        values.put("name", "JUnit test");

        ObjectMapper mapper = new ObjectMapper();

        LabelTemplateDescriptor descriptor = mapper.readValue(
                IOUtils.toString(getClass().getResource("/template01.svg-labels.json")),
                LabelTemplateDescriptor.class
        );

        InstanceRenderer renderer = new InstanceRenderer();
        String renderedInstance = renderer.render(
                IOUtils.toString(getClass().getResource("/template01.svg")),
                descriptor,
                values
        );

        System.out.println(renderedInstance);
    }
}
