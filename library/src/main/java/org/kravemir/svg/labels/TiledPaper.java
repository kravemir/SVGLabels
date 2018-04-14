package org.kravemir.svg.labels;

import org.kravemir.svg.labels.annotations.ToBePublicApi;

/**
 * The <code>TilerPaper</code> is a value class, and represents a paper containing tiled labels.
 *
 * Use a {@link Builder} to construct instance.
 */
// TODO: support different types of units
@ToBePublicApi
public class TiledPaper {
    private double width;
    private double height;
    private double tileOffsetX;
    private double tileOffsetY;
    private double tileWidth;
    private double tileHeight;
    private double tileDeltaX;
    private double tileDeltaY;

    // TODO: consistency of construction by Builder
    private TiledPaper() {
    }

    @ToBePublicApi
    public double getWidth() {
        return width;
    }

    @ToBePublicApi
    public double getHeight() {
        return height;
    }

    @ToBePublicApi
    public double getTileOffsetX() {
        return tileOffsetX;
    }

    @ToBePublicApi
    public double getTileOffsetY() {
        return tileOffsetY;
    }

    @ToBePublicApi
    public double getTileWidth() {
        return tileWidth;
    }

    @ToBePublicApi
    public double getTileHeight() {
        return tileHeight;
    }

    @ToBePublicApi
    public double getTileDeltaX() {
        return tileDeltaX;
    }

    @ToBePublicApi
    public double getTileDeltaY() {
        return tileDeltaY;
    }

    @ToBePublicApi
    public static class Builder {
        private double width;
        private double height;
        private double tileOffsetX;
        private double tileOffsetY;
        private double tileWidth;
        private double tileHeight;
        private double tileDeltaX;
        private double tileDeltaY;

        @ToBePublicApi
        public Builder withPaperSize(double width, double height) {
            this.width = width;
            this.height = height;
            return this;
        }

        @ToBePublicApi
        public Builder withLabelSize(double labelWidth, double labelHeight) {
            this.tileWidth = labelWidth;
            this.tileHeight = labelHeight;
            return this;
        }

        @ToBePublicApi
        public Builder withLabelOffset(double labelOffsetX, double labelOffsetY) {
            this.tileOffsetX = labelOffsetX;
            this.tileOffsetY = labelOffsetY;
            return this;
        }

        @ToBePublicApi
        public Builder withLabelDelta(double labelDeltaX, double labelDeltaY) {
            this.tileDeltaX = labelDeltaX;
            this.tileDeltaY = labelDeltaY;
            return this;
        }

        @ToBePublicApi
        public Builder withWidth(double width) {
            this.width = width;
            return this;
        }

        @ToBePublicApi
        public Builder withHeight(double height) {
            this.height = height;
            return this;
        }

        @ToBePublicApi
        public Builder withTileOffsetX(double tileOffsetX) {
            this.tileOffsetX = tileOffsetX;
            return this;
        }

        @ToBePublicApi
        public Builder withTileOffsetY(double tileOffsetY) {
            this.tileOffsetY = tileOffsetY;
            return this;
        }

        @ToBePublicApi
        public Builder withTileWidth(double tileWidth) {
            this.tileWidth = tileWidth;
            return this;
        }

        @ToBePublicApi
        public Builder withTileHeight(double tileHeight) {
            this.tileHeight = tileHeight;
            return this;
        }

        @ToBePublicApi
        public Builder withTileDeltaX(double tileDeltaX) {
            this.tileDeltaX = tileDeltaX;
            return this;
        }

        @ToBePublicApi
        public Builder withTileDeltaY(double tileDeltaY) {
            this.tileDeltaY = tileDeltaY;
            return this;
        }

        @ToBePublicApi
        public TiledPaper build() {
            TiledPaper paper = new TiledPaper();
            paper.width = this.width;
            paper.height = this.height;
            paper.tileOffsetX = this.tileOffsetX;
            paper.tileOffsetY = this.tileOffsetY;
            paper.tileWidth = this.tileWidth;
            paper.tileHeight = this.tileHeight;
            paper.tileDeltaX = this.tileDeltaX;
            paper.tileDeltaY = this.tileDeltaY;
            return paper;
        }
    }

    /**
     * Creates a new instance of a {@link Builder}
     *
     * @return new {@link Builder} instance
     */
    @ToBePublicApi
    public static Builder builder() {
        return new Builder();
    }
}
