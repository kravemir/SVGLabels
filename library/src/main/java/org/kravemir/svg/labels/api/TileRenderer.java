package org.kravemir.svg.labels.api;

import org.w3c.dom.svg.SVGDocument;

import java.util.List;

public interface TileRenderer {

    String render(TiledPaper paper, String SVG);
    String renderPositions(TiledPaper paper);

    List<SVGDocument> render(TiledPaper paper, List<LabelGroup> labels, DocumentRenderOptions options);
}
