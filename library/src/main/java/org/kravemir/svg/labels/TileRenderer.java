package org.kravemir.svg.labels;

import org.kravemir.svg.labels.annotations.ToBePublicApi;
import org.w3c.dom.svg.SVGDocument;

import java.util.List;

@ToBePublicApi
public interface TileRenderer {

    String render(TiledPaper paper, String SVG);
    String renderPositions(TiledPaper paper);

    @ToBePublicApi
    List<SVGDocument> render(TiledPaper paper, List<LabelGroup> labels, DocumentRenderOptions options);
}
