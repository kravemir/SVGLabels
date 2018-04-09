package org.kravemir.svg.labels.api;

import org.w3c.dom.svg.SVGDocument;

public class LabelGroup {

    public static final int FILL_PAGE = 0;

    private SVGDocument template;
    private int count;

    public LabelGroup(SVGDocument template, int count) {
        this.template = template;
        this.count = count;
    }

    public SVGDocument getTemplate() {
        return template;
    }

    public void setTemplate(SVGDocument template) {
        this.template = template;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
