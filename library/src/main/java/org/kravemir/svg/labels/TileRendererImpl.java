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

    static class TilePositioner{
        private SVGDocument document;
        private TiledPaper paper;
        private double x, y;
        private DocumentRenderOptions options;

        TilePositioner(TiledPaper p, DocumentRenderOptions m){
            paper = p;
            options = m;
            x = -1;
        }

        void createDocument(){
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
        }

        boolean empty(){
            return x < 0;
        }

        boolean nextPosition(){
            if(document == null) return false;

            if(x < 0){
                x = paper.getTileOffsetX();
                y = paper.getTileOffsetY();
            }else {
                x += paper.getTileWidth() + paper.getTileDeltaX();
                if (x > paper.getWidth() - paper.getTileWidth()) {
                    x = paper.getTileOffsetX();

                    y += paper.getTileHeight() + paper.getTileDeltaY();
                    if (y > paper.getHeight() - paper.getTileHeight())
                        return false;
                }
            }

            if(options.isRenderTileBorders())
                document.getDocumentElement().appendChild(createRect(document,x , y, paper.getTileWidth(), paper.getTileHeight()));

            return true;
        }

        SVGDocument getDocument() {
            return document;
        }

        double getX() {
            return x;
        }

        double getY() {
            return y;
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
        TilePositioner positioner = new TilePositioner(paper, options);
        positioner.createDocument();

        for(LabelGroup l : labels){

            Document templateDoc = parseSVG(l.getTemplate());
            Element templateRoot = null;
            double labelW = 0, labelH = 0;
            if(templateDoc != null){
                templateRoot = templateDoc.getDocumentElement();
                labelW = length(templateRoot.getAttributeNS(null,"width"));
                labelH = length(templateRoot.getAttributeNS(null,"height"));
            }
            double labelOffsetX = (paper.getTileWidth() - labelW) / 2;
            double labelOffsetY = (paper.getTileHeight()- labelH) / 2;

            for(int n = 0; n < l.getCount() || l.shouldFillPage(); n++){

                //create new page if current is full
                if(!positioner.nextPosition()){
                    documents.add(positioner.getDocument());
                    positioner.createDocument();

                    //move to next template if page is full
                    if(l.shouldFillPage()) break;

                    positioner.nextPosition();
                }

                Document doc = positioner.getDocument();
                Element root = doc.getDocumentElement();

                if (options.isRenderLabelBorders())
                    root.appendChild(createRect(doc, positioner.getX() + labelOffsetX, positioner.getY() + labelOffsetY, labelW, labelH));

                if(templateDoc != null) {
                    Element label = (Element) templateRoot.cloneNode(true);
                    doc.adoptNode(label);
                    label.setAttributeNS(null, "x", length(positioner.getX() + labelOffsetX));
                    label.setAttributeNS(null, "y", length(positioner.getY() + labelOffsetY));
                    label.setAttributeNS(null, "width", length(labelW));
                    label.setAttributeNS(null, "height", length(labelH));
                    root.appendChild(label);
                }
            }
        }

        if(!positioner.empty())
            documents.add(positioner.getDocument());

        return documents;
    }

    @Override
    public String render(TiledPaper paper, String SVG) {
        LabelGroup l = LabelGroup.builder().setTemplate(SVG).fillPage().build();
        return render(paper, Collections.singletonList(l), DocumentRenderOptions.builder().build()).get(0);
    }
}
