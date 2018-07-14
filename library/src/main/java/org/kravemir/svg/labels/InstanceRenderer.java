package org.kravemir.svg.labels;

import org.kravemir.svg.labels.annotations.ToBePublicApi;
import org.kravemir.svg.labels.model.LabelTemplateDescriptor;

import javax.xml.xpath.XPathExpressionException;
import java.util.Map;

@ToBePublicApi
public interface InstanceRenderer {

    interface PreparedInstance {
        public String render(Map<String, String> instanceContent);
    }

    PreparedInstance prepare(String svgTemplate, LabelTemplateDescriptor descriptor);

    @ToBePublicApi
    String render(String svgTemplate,
                  LabelTemplateDescriptor templateDescriptor,
                  Map<String, String> instanceContent) throws XPathExpressionException;
}
