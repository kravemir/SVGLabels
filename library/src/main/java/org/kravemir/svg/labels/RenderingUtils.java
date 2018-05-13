package org.kravemir.svg.labels;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.svg.SVGDocument;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class RenderingUtils {
    public static SVGDocument parseSVG(String s) {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        try {
            return factory.createSVGDocument("", new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            // TODO: do something nicer
            throw new RuntimeException(s);
        }
    }

    public static String documentToString(Document doc) {
        try {
            StringWriter writer = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.toString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
