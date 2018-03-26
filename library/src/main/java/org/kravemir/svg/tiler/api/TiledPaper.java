package org.kravemir.svg.tiler.api;

public interface TiledPaper {

    /**
     * width of paper in mm
     * @return
     */
    double getWidth();

    /**
     * height of paper in mm
     * @return
     */
    double getHeight();

    /**
     * x-offset of first tile
     * @return
     */
    double getTileOffsetX();

    /**
     * y-offset of first tile
     */
    double getTileOffsetY();

    /**
     * tile width
     * @return
     */
    double getTileWidth();

    /**
     * tile height
     */
    double getTileHeight();

    /**
     * tile spacing
     */
    double getTileDeltaX();

    /**
     * tile spacing
     */
    double getTileDeltaY();
}
