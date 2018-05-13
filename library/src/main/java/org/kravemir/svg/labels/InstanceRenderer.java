package org.kravemir.svg.labels;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

import javax.xml.xpath.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InstanceRenderer {

    public String render(String svgTemplate,
                         LabelTemplateDescriptor templateDescriptor,
                         Map<String,String> instanceContent) throws XPathExpressionException {

        SVGDocument document = RenderingUtils.parseSVG(svgTemplate);
        XPath xpath = XPathFactory.newInstance().newXPath();

        for (LabelTemplateDescriptor.ContentReplaceRule rule : templateDescriptor.getContentReplaceRules()) {
            XPathExpression elementXPath = xpath.compile(rule.getElementXPath());
            XPathExpression tspanXPath = xpath.compile("*[local-name()='tspan']");

            String value = evaluateValue(rule, instanceContent);

            getNodeStream(document.getRootElement(), elementXPath).forEach(node -> {
                getNodeStream(node, tspanXPath).forEach(tspanNode -> {
                    tspanNode.setTextContent(value);
                });
            });
        }

        return RenderingUtils.documentToString(document);
    }

    private String evaluateValue(LabelTemplateDescriptor.ContentReplaceRule rule, Map<String, String> instanceContent) {
        Pattern p = Pattern.compile("\\$([a-zA-Z]+)");
        Matcher m = p.matcher(rule.getValue());
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String variableName = m.group(1);
            String value = instanceContent.get(variableName);
            m.appendReplacement(sb, value);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private Stream<Node> getNodeStream(Node root, XPathExpression expression){
        try {
            NodeList nodes = (NodeList) expression.evaluate(root, XPathConstants.NODESET);
            return IntStream.range(0, nodes.getLength()).mapToObj(nodes::item);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

}
