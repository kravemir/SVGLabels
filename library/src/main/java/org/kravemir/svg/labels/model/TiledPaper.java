package org.kravemir.svg.labels.model;

import com.google.auto.value.AutoValue;
import org.kravemir.svg.labels.annotations.ToBePublicApi;

/**
 * The <code>TilerPaper</code> is a value class, and represents a paper containing tiled labels.
 *
 * Use a {@link Builder} to construct instance.
 */
// TODO: support different types of units
@AutoValue
@ToBePublicApi
public abstract class TiledPaper {

    protected TiledPaper() {
    }

    @ToBePublicApi
    public abstract double getWidth();

    @ToBePublicApi
    public abstract double getHeight();

    @ToBePublicApi
    public abstract double getTileOffsetX();

    @ToBePublicApi
    public abstract double getTileOffsetY();

    @ToBePublicApi
    public abstract double getTileWidth();

    @ToBePublicApi
    public abstract double getTileHeight();

    @ToBePublicApi
    public abstract double getTileDeltaX();

    @ToBePublicApi
    public abstract double getTileDeltaY();

    @AutoValue.Builder
    @ToBePublicApi
    public abstract static class Builder {

        @ToBePublicApi
        public Builder setPaperSize(double width, double height) {
            setWidth(width);
            setHeight(height);
            return this;
        }

        @ToBePublicApi
        public Builder setLabelSize(double labelWidth, double labelHeight) {
            setTileWidth(labelWidth);
            setTileHeight(labelHeight);
            return this;
        }

        @ToBePublicApi
        public Builder setLabelOffset(double labelOffsetX, double labelOffsetY) {
            setTileOffsetX(labelOffsetX);
            setTileOffsetY(labelOffsetY);
            return this;
        }

        @ToBePublicApi
        public Builder setLabelDelta(double labelDeltaX, double labelDeltaY) {
            setTileDeltaX(labelDeltaX);
            setTileDeltaY(labelDeltaY);
            return this;
        }

        @ToBePublicApi
        public abstract Builder setWidth(double width);

        @ToBePublicApi
        public abstract Builder setHeight(double height);

        @ToBePublicApi
        public abstract Builder setTileOffsetX(double tileOffsetX);

        @ToBePublicApi
        public abstract Builder setTileOffsetY(double tileOffsetY);

        @ToBePublicApi
        public abstract Builder setTileWidth(double tileWidth);

        @ToBePublicApi
        public abstract Builder setTileHeight(double tileHeight);

        @ToBePublicApi
        public abstract Builder setTileDeltaX(double tileDeltaX);

        @ToBePublicApi
        public abstract Builder setTileDeltaY(double tileDeltaY);

        @ToBePublicApi
        public abstract TiledPaper build();
    }

    /**
     * Creates a new instance of a {@link Builder}
     *
     * @return new {@link Builder} instance
     */
    @ToBePublicApi
    public static Builder builder() {
        return new AutoValue_TiledPaper.Builder();
    }
}
