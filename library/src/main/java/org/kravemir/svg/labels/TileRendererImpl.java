package org.kravemir.svg.labels;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.anim.dom.SVGOMDocument;
import org.apache.batik.anim.dom.SVGOMSVGElement;
import org.apache.batik.dom.svg.SVGContext;
import org.kravemir.svg.labels.model.DocumentRenderOptions;
import org.kravemir.svg.labels.model.LabelGroup;
import org.kravemir.svg.labels.model.TiledPaper;
import org.kravemir.svg.labels.utils.RenderingUtils;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGLength;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.kravemir.svg.labels.utils.RenderingUtils.parseSVG;

/**
 * Implementation of {@link TileRenderer}
 */
public class TileRendererImpl implements TileRenderer {

    // helper class for length()
    static class DummySVGContext implements SVGContext {
        @Override
        public float getPixelUnitToMillimeter() { return 1; }

        @Override
        public float getPixelToMM() { return 1; }

        @Override
        public Rectangle2D getBBox() { return null; }

        @Override
        public AffineTransform getScreenTransform() { return null; }

        @Override
        public void setScreenTransform(AffineTransform at) { }

        @Override
        public AffineTransform getCTM() { return null;
        }

        @Override
        public AffineTransform getGlobalTransform() { return null; }

        @Override
        public float getViewportWidth() { return 0; }

        @Override
        public float getViewportHeight() { return 0;}

        @Override
        public float getFontSize() { return 0; }
    }

    static SVGDocument createSVG() {
        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        return (SVGOMDocument)impl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);
    }

    static Element createRect(Document doc, double x, double y, double w, double h){
        Element r = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");
        r.setAttributeNS(null, "style", "fill:none;stroke:black;stroke-width:0.5");
        r.setAttributeNS(null, "x", x+"mm");
        r.setAttributeNS(null, "y", y+"mm");
        r.setAttributeNS(null, "width", w+"mm");
        r.setAttributeNS(null, "height", h+"mm");
        return r;
    }

    private static final short LENGTH_TYPE = SVGLength.SVG_LENGTHTYPE_MM;
    private static final String LENGTH_STRING = "mm";

    static String length(double d){
        return Double.toString(d) + LENGTH_STRING;
    }

    //todo find better solution
    static double length(String s){
        SVGOMDocument doc = (SVGOMDocument)createSVG();
        SVGOMSVGElement root = (SVGOMSVGElement)doc.getRootElement();

        //fix for SVGLength support
        doc.setSVGContext(new DummySVGContext());
        root.setSVGContext(doc.getSVGContext());

        SVGLength lw = root.createSVGLength();
        lw.setValueAsString(s);
        lw.convertToSpecifiedUnits(LENGTH_TYPE);
        return lw.getValueInSpecifiedUnits();
    }

    static class LabelTemplate {
        Element templateRoot = null;
        double labelW = 0, labelH = 0;
        double labelOffsetX = 0, labelOffsetY = 0;

        public static LabelTemplate create(String svg, TiledPaper paper) {
            LabelTemplate template = new LabelTemplate();
            template.load(svg, paper);
            return template;
        }

        public void load(String svg, TiledPaper paper) {
            Document templateDoc = parseSVG(svg);
            if (templateDoc != null) {
                templateRoot = templateDoc.getDocumentElement();
                labelW = length(templateRoot.getAttributeNS(null, "width"));
                labelH = length(templateRoot.getAttributeNS(null, "height"));
                labelOffsetX = (paper.getTileWidth() - labelW) / 2;
                labelOffsetY = (paper.getTileHeight() - labelH) / 2;
            }
        }
    }

    static class LabelDocumentBuilder {
        private SVGDocument document;
        private TiledPaper paper;
        private double x, y;
        private DocumentRenderOptions options;
        boolean full;

        LabelDocumentBuilder(TiledPaper p, DocumentRenderOptions m){
            paper = p;
            options = m;
            x = -1;
        }

        void startDocument(){
            DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
            document = (SVGDocument) impl.createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);
            Element root = document.getDocumentElement();

            //set page size
            root.setAttributeNS(null, "width", length(paper.getWidth()));
            root.setAttributeNS(null, "height", length(paper.getHeight()));
            //root.setAttributeNS(null, "viewBox", "0 0 " + lw.getValueAsString() + " " + lh.getValueAsString());

            if(options.isRenderPageBorders())
                root.appendChild(createRect(document,0,0, paper.getWidth(), paper.getHeight()));

            x = -1;

            full = false;
            x = paper.getTileOffsetX();
            y = paper.getTileOffsetY();
        }


        public void placeLabel(LabelTemplate template) {
            Element root = document.getDocumentElement();

            if( options.isRenderTileBorders())
                document.getDocumentElement().appendChild(createRect(document,x , y, paper.getTileWidth(), paper.getTileHeight()));

            if (options.isRenderLabelBorders())
                root.appendChild(createRect(document, x + template.labelOffsetX, y + template.labelOffsetY, template.labelW, template.labelH));

            Element label = (Element) template.templateRoot.cloneNode(true);
            document.adoptNode(label);
            label.setAttributeNS(null, "x", length(x + template.labelOffsetX));
            label.setAttributeNS(null, "y", length(y + template.labelOffsetY));
            label.setAttributeNS(null, "width", length(template.labelW));
            label.setAttributeNS(null, "height", length(template.labelH));
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

        boolean isFull() {
            return document == null || full;
        }

        SVGDocument getDocument() {
            return document;
        }
    }

    /*
        TODO: scaling, exceptions
     */

    @Override
    public List<String> render(TiledPaper paper, List<LabelGroup> labels, DocumentRenderOptions options) {
        return renderAsSVGDocument(paper, labels, options)
                .stream()
                .map(RenderingUtils::documentToString)
                .collect(Collectors.toList());
    }

    public List<SVGDocument> renderAsSVGDocument(TiledPaper paper, List<LabelGroup> labels, DocumentRenderOptions options) {

        ArrayList<SVGDocument> documents = new ArrayList<>();
        LabelDocumentBuilder builder = new LabelDocumentBuilder(paper, options);
        builder.startDocument();

        for(LabelGroup l : labels){
            LabelTemplate template = LabelTemplate.create(l.getTemplate(), paper);

            for(int n = 0; n < l.getCount() || l.shouldFillPage(); n++){
                builder.placeLabel(template);

                //create new page if current is full
                if(builder.isFull()){
                    documents.add(builder.getDocument());
                    builder.startDocument();

                    //move to next template if page is full
                    if(l.shouldFillPage()) break;
                }
            }
        }

        return documents;
    }

    @Override
    public String render(TiledPaper paper, String SVG) {
        LabelGroup l = LabelGroup.builder().setTemplate(SVG).fillPage().build();
        return render(paper, Collections.singletonList(l), DocumentRenderOptions.builder().build()).get(0);
    }
}
