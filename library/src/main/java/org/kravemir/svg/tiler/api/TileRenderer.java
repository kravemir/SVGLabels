package org.kravemir.svg.tiler.api;

import org.w3c.dom.svg.SVGDocument;

import java.util.List;

public interface TileRenderer {

    int RENDER_PAGE_BORDERS = 1;
    int RENDER_TILE_BORSERS = 2;
    int RENDER_LABEL_BORSERS = 4;

    String render(TiledPaper paper, String SVG);
    String renderPositions(TiledPaper paper);

    List<SVGDocument> render(TiledPaper paper, List<LabelGroup> labels, int mode);
}
