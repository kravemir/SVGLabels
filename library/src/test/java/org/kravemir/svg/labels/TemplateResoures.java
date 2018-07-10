package org.kravemir.svg.labels;

import org.apache.commons.collections4.MapUtils;
import org.hamcrest.Matcher;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.kravemir.svg.labels.matcher.NodesMatchingXPath.nodesMatchingXPath;

public final class TemplateResoures {

    static final Map<String,String> DATA_01 = MapUtils.putAll(new HashMap<>(), new String[][] {
            {"name", "JUnit test"},
            {"description", "Test replacement of texts"},
            {"date", "13. 05. 2017"}
    });

    static final Matcher<Node> TEMPLATE_01_MATCHER = allOf(
            nodesMatchingXPath( ".//*[@id='nameText']/*[1][text()='Multiline']", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='nameText']/*[2][text()='name']", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='nameText']/*", hasSize(2))
    );
    static final Matcher<Node> TEMPLATE_01_DATA_01_MATCHER = allOf(
            nodesMatchingXPath( ".//*[@id='nameText']/*[1][text()='JUnit test']", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='nameText']/*[2][not(text())]", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='nameText']/*", hasSize(2)),
            nodesMatchingXPath( "//*[@id='text4540']/*[text()='Test replacement of texts']", hasSize(1)),
            nodesMatchingXPath( "//*[@id='text4544']/*[text()='13. 05. 2017']", hasSize(1))
    );
    static final Matcher<Node> TEMPLATE_02_MATCHER = allOf(
            nodesMatchingXPath( ".//*[@id='text-large']/*[1][text()='Large font']", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='text-large']/*[2][text()='TEXT']", hasSize(1)),
            nodesMatchingXPath( ".//*[@id='text-large']/*", hasSize(2))
    );
}
