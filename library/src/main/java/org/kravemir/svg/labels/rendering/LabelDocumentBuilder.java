package org.kravemir.svg.labels.rendering;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.kravemir.svg.labels.model.DocumentRenderOptions;
import org.kravemir.svg.labels.model.TiledPaper;
import org.kravemir.svg.labels.utils.RenderingUtils;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

public class LabelDocumentBuilder {
    private SVGDocument document;
    private TiledPaper paper;
    private double x, y;
    private DocumentRenderOptions options;
    boolean full;

    public LabelDocumentBuilder(TiledPaper p, DocumentRenderOptions m){
        paper = p;
        options = m;
        x = -1;
    }

    public void startDocument(){
        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        document = (SVGDocument) impl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);
        Element root = document.getDocumentElement();

        //set page size
        root.setAttributeNS(null, "width", RenderingUtils.length(paper.getWidth()));
        root.setAttributeNS(null, "height", RenderingUtils.length(paper.getHeight()));
        //root.setAttributeNS(null, "viewBox", "0 0 " + lw.getValueAsString() + " " + lh.getValueAsString());

        if(options.isRenderPageBorders())
            root.appendChild(RenderingUtils.createRect(document,0,0, paper.getWidth(), paper.getHeight()));

        x = -1;

        full = false;
        x = paper.getTileOffsetX();
        y = paper.getTileOffsetY();
    }


    public void placeLabel(LabelTemplate template) {
        Element root = document.getDocumentElement();

        if( options.isRenderTileBorders())
            document.getDocumentElement().appendChild(RenderingUtils.createRect(document,x , y, paper.getTileWidth(), paper.getTileHeight()));

        if (options.isRenderLabelBorders())
            root.appendChild(RenderingUtils.createRect(document, x + template.labelOffsetX, y + template.labelOffsetY, template.labelW, template.labelH));

        Element label = (Element) template.templateRoot.cloneNode(true);
        document.adoptNode(label);
        label.setAttributeNS(null, "x", RenderingUtils.length(x + template.labelOffsetX));
        label.setAttributeNS(null, "y", RenderingUtils.length(y + template.labelOffsetY));
        label.setAttributeNS(null, "width", RenderingUtils.length(template.labelW));
        label.setAttributeNS(null, "height", RenderingUtils.length(template.labelH));
        root.appendChild(label);

        nextPosition();
    }

    private void nextPosition(){
        if(isFull()) return;

        x += paper.getTileWidth() + paper.getTileDeltaX();
        if (x > paper.getWidth() - paper.getTileWidth()) {
            x = paper.getTileOffsetX();
            y += paper.getTileHeight() + paper.getTileDeltaY();

            if (y > paper.getHeight() - paper.getTileHeight()) {
                full = true;
            }
        }
    }

    public boolean isFull() {
        return document == null || full;
    }

    public SVGDocument getDocument() {
        return document;
    }
}
