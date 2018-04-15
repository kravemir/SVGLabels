package org.kravemir.svg.labels;

import org.kravemir.svg.labels.annotations.ToBePublicApi;
import org.w3c.dom.svg.SVGDocument;

import java.util.List;

/**
 * Renders label documents from paper and label specifications.
 */
@ToBePublicApi
public interface TileRenderer {

    // TODO: think about names of simplication methods

    /**
     * Renders one page filled by single label
     *
     * @param paper specification of a paper
     * @param SVG label SVG image
     * @return generate page
     */
    String render(TiledPaper paper, String SVG);

    /**
     * Renders labels
     *
     * @param paper specification of a paper
     * @param labels specification of labels to be rendered
     * @param options rendering options
     * @return generated pages as a SVG
     */
    @ToBePublicApi
    List<String> render(TiledPaper paper, List<LabelGroup> labels, DocumentRenderOptions options);
}
