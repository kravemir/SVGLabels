package org.kravemir.svg.labels;

/**
 * paper specification, in mm (units)
 *
 * TODO: support different types of units
 */
public class TiledPaper {
    private double width;
    private double height;
    private double tileOffsetX;
    private double tileOffsetY;
    private double tileWidth;
    private double tileHeight;
    private double tileDeltaX;
    private double tileDeltaY;

    private TiledPaper() {
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

    public static class Builder {
        private double width;
        private double height;
        private double tileOffsetX;
        private double tileOffsetY;
        private double tileWidth;
        private double tileHeight;
        private double tileDeltaX;
        private double tileDeltaY;

        public Builder withPaperSize(double width, double height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder withLabelSize(double labelWidth, double labelHeight) {
            this.tileWidth = labelWidth;
            this.tileHeight = labelHeight;
            return this;
        }

        public Builder withLabelOffset(double labelOffsetX, double labelOffsetY) {
            this.tileOffsetX = labelOffsetX;
            this.tileOffsetY = labelOffsetY;
            return this;
        }

        public Builder withLabelDelta(double labelDeltaX, double labelDeltaY) {
            this.tileDeltaX = labelDeltaX;
            this.tileDeltaY = labelDeltaY;
            return this;
        }

        public Builder withWidth(double width) {
            this.width = width;
            return this;
        }

        public Builder withHeight(double height) {
            this.height = height;
            return this;
        }

        public Builder withTileOffsetX(double tileOffsetX) {
            this.tileOffsetX = tileOffsetX;
            return this;
        }

        public Builder withTileOffsetY(double tileOffsetY) {
            this.tileOffsetY = tileOffsetY;
            return this;
        }

        public Builder withTileWidth(double tileWidth) {
            this.tileWidth = tileWidth;
            return this;
        }

        public Builder withTileHeight(double tileHeight) {
            this.tileHeight = tileHeight;
            return this;
        }

        public Builder withTileDeltaX(double tileDeltaX) {
            this.tileDeltaX = tileDeltaX;
            return this;
        }

        public Builder withTileDeltaY(double tileDeltaY) {
            this.tileDeltaY = tileDeltaY;
            return this;
        }

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

    public static Builder builder() {
        return new Builder();
    }
}
