package org.kravemir.svg.labels;

import org.kravemir.svg.labels.utils.ExpressionEvaluator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

import javax.xml.xpath.*;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InstanceRendererImpl implements InstanceRenderer {

    private final ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();

    @Override
    public String render(String svgTemplate,
                         LabelTemplateDescriptor templateDescriptor,
                         Map<String, String> instanceContent) throws XPathExpressionException {

        SVGDocument document = RenderingUtils.parseSVG(svgTemplate);
        XPath xpath = XPathFactory.newInstance().newXPath();

        for (LabelTemplateDescriptor.ContentReplaceRule rule : templateDescriptor.getContentReplaceRules()) {
            XPathExpression elementXPath = xpath.compile(rule.getElementXPath());
            XPathExpression tspanXPath = xpath.compile("*[local-name()='tspan']");

            String value = expressionEvaluator.evaluateExpression(rule.getValue(), instanceContent);
            String[] valueLines = value.split("\n");

            getNodeStream(document.getRootElement(), elementXPath).forEach(node -> {
                NodeList spanNodes = getNodes(node, tspanXPath);
                int line = 0;
                for(; line < Math.min(spanNodes.getLength(), valueLines.length); line++ ) {
                    Node tspanNode = spanNodes.item(line);
                    tspanNode.setTextContent(valueLines[line]);
                }
                for(; line < spanNodes.getLength(); line++ ) {
                    Node tspanNode = spanNodes.item(line);
                    tspanNode.setTextContent("");
                }
            });
        }

        return RenderingUtils.documentToString(document);
    }

    private Stream<Node> getNodeStream(Node root, XPathExpression expression){
        NodeList nodes = getNodes(root, expression);
        return IntStream.range(0, nodes.getLength()).mapToObj(nodes::item);
    }

    private NodeList getNodes(Node root, XPathExpression expression){
        try {
            return (NodeList) expression.evaluate(root, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

}
