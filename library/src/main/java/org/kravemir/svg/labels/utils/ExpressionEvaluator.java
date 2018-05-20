package org.kravemir.svg.labels.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionEvaluator {

    private Pattern p = Pattern.compile(
            "\\$(?:([a-zA-Z]+)|\\{([a-zA-Z]+)})"
    );

    public String evaluateExpression(String expression, Map<String,String> variables) {

        Matcher m = p.matcher(expression);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String variableName = m.group(1);
            if(variableName == null)
                variableName = m.group(2);

            String value = variables.get(variableName);
            m.appendReplacement(sb, value);
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
