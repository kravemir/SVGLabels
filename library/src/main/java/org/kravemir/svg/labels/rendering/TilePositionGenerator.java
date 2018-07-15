package org.kravemir.svg.labels.rendering;

import org.kravemir.svg.labels.annotations.ToBePublicApi;
import org.kravemir.svg.labels.model.TiledPaper;

public class TilePositionGenerator {
    private final TiledPaper paper;

    private double x, y;
    private boolean full = true;

    public TilePositionGenerator(TiledPaper paper) {
        this.paper = paper;
    }

    public void start(){
        full = false;
        x = paper.tileOffsetX();
        y = paper.tileOffsetY();
    }

    public void nextPosition(){
        if(isFull()) return;

        x += paper.tileWidth() + paper.tileDeltaX();
        if (x > paper.width() - paper.tileWidth()) {
            x = paper.tileOffsetX();
            y += paper.tileHeight() + paper.tileDeltaY();

            if (y > paper.height() - paper.tileHeight()) {
                full = true;
            }
        }
    }

    public boolean isFull() {
        return full;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getPaperWidth() {
        return paper.width();
    }

    public double getPaperHeight() {
        return paper.height();
    }

    @ToBePublicApi
    public double getTileWidth() {
        return paper.tileWidth();
    }

    @ToBePublicApi
    public double getTileHeight() {
        return paper.tileHeight();
    }
}
