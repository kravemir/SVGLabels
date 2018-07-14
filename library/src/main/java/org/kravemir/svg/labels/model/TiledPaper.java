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
    public abstract double width();

    @ToBePublicApi
    public abstract double height();

    @ToBePublicApi
    public abstract double tileOffsetX();

    @ToBePublicApi
    public abstract double tileOffsetY();

    @ToBePublicApi
    public abstract double tileWidth();

    @ToBePublicApi
    public abstract double tileHeight();

    @ToBePublicApi
    public abstract double tileDeltaX();

    @ToBePublicApi
    public abstract double tileDeltaY();

    @AutoValue.Builder
    @ToBePublicApi
    public abstract static class Builder {

        @ToBePublicApi
        public Builder paperSize(double width, double height) {
            width(width);
            height(height);
            return this;
        }

        @ToBePublicApi
        public Builder labelSize(double labelWidth, double labelHeight) {
            tileWidth(labelWidth);
            tileHeight(labelHeight);
            return this;
        }

        @ToBePublicApi
        public Builder labelOffset(double labelOffsetX, double labelOffsetY) {
            tileOffsetX(labelOffsetX);
            tileOffsetY(labelOffsetY);
            return this;
        }

        @ToBePublicApi
        public Builder labelDelta(double labelDeltaX, double labelDeltaY) {
            tileDeltaX(labelDeltaX);
            tileDeltaY(labelDeltaY);
            return this;
        }

        @ToBePublicApi
        public abstract Builder width(double width);

        @ToBePublicApi
        public abstract Builder height(double height);

        @ToBePublicApi
        public abstract Builder tileOffsetX(double tileOffsetX);

        @ToBePublicApi
        public abstract Builder tileOffsetY(double tileOffsetY);

        @ToBePublicApi
        public abstract Builder tileWidth(double tileWidth);

        @ToBePublicApi
        public abstract Builder tileHeight(double tileHeight);

        @ToBePublicApi
        public abstract Builder tileDeltaX(double tileDeltaX);

        @ToBePublicApi
        public abstract Builder tileDeltaY(double tileDeltaY);

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
