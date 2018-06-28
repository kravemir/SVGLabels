package org.kravemir.svg.labels;

import org.kravemir.svg.labels.model.DocumentRenderOptions;
import org.kravemir.svg.labels.model.LabelGroup;
import org.kravemir.svg.labels.model.TiledPaper;
import org.kravemir.svg.labels.rendering.LabelDocumentBuilder;
import org.kravemir.svg.labels.rendering.LabelTemplate;
import org.kravemir.svg.labels.utils.RenderingUtils;
import org.w3c.dom.svg.SVGDocument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link TileRenderer}
 */
public class TileRendererImpl implements TileRenderer {

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
