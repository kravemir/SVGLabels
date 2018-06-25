package org.kravemir.svg.labels;

import org.kravemir.svg.labels.annotations.ToBePublicApi;
import org.kravemir.svg.labels.model.LabelTemplateDescriptor;

import javax.xml.xpath.XPathExpressionException;
import java.util.Map;

@ToBePublicApi
public interface InstanceRenderer {

    @ToBePublicApi
    String render(String svgTemplate,
                  LabelTemplateDescriptor templateDescriptor,
                  Map<String, String> instanceContent) throws XPathExpressionException;
}
