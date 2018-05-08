import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.kravemir.svg.labels.LabelTemplateDescriptor;

import java.io.IOException;

public class TestTemplateDescriptionJSON {

    @Test
    public void test() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        LabelTemplateDescriptor descriptor = mapper.readValue(
                IOUtils.toString(getClass().getResource("/template01.svg-labels.json")),
                LabelTemplateDescriptor.class
        );

        System.out.println(descriptor);
        System.out.println(mapper.writeValueAsString(descriptor));
    }
}
