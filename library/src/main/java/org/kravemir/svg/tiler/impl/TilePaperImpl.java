package org.kravemir.svg.tiler.impl;

import org.kravemir.svg.tiler.api.TiledPaper;

/**
 * Created by miroslav on 4.12.16.
 */
public class TilePaperImpl implements TiledPaper {
    private double width;
    private double height;
    private double tileOffsetX;
    private double tileOffsetY;
    private double tileWidth;
    private double tileHeight;
    private double tileDeltaX;
    private double tileDeltaY;

    public TilePaperImpl() {
    }

    public TilePaperImpl(double width, double height, double tileOffsetX, double tileOffsetY, double tileWidth, double tileHeight) {
        this(width,height,tileOffsetX,tileOffsetY,tileWidth,tileHeight,0,0);
    }

    public TilePaperImpl(double width, double height, double tileOffsetX, double tileOffsetY, double tileWidth, double tileHeight, double tileDeltaX, double tileDeltaY) {
        this.width = width;
        this.height = height;
        this.tileOffsetX = tileOffsetX;
        this.tileOffsetY = tileOffsetY;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tileDeltaX = tileDeltaX;
        this.tileDeltaY = tileDeltaY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getTileOffsetX() {
        return tileOffsetX;
    }

    public double getTileOffsetY() {
        return tileOffsetY;
    }

    public double getTileWidth() {
        return tileWidth;
    }

    public double getTileHeight() {
        return tileHeight;
    }

    public double getTileDeltaX() {
        return tileDeltaX;
    }

    public double getTileDeltaY() {
        return tileDeltaY;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setTileOffsetX(double tileOffsetX) {
        this.tileOffsetX = tileOffsetX;
    }

    public void setTileOffsetY(double tileOffsetY) {
        this.tileOffsetY = tileOffsetY;
    }

    public void setTileWidth(double tileWidth) {
        this.tileWidth = tileWidth;
    }

    public void setTileHeight(double tileHeight) {
        this.tileHeight = tileHeight;
    }

    public void setTileDeltaX(double tileDeltaX) {
        this.tileDeltaX = tileDeltaX;
    }

    public void setTileDeltaY(double tileDeltaY) {
        this.tileDeltaY = tileDeltaY;
    }
}
