package org.kravemir.svg.labels.model;

import com.google.auto.value.AutoValue;
import org.kravemir.svg.labels.annotations.ToBePublicApi;

/**
 * The <code>DocumentRenderOptions</code> is a value class, and contains rendering options.
 *
 * Use a {@link Builder} to construct instance.
 */
@AutoValue
@ToBePublicApi
public abstract class DocumentRenderOptions {

    protected DocumentRenderOptions() {
    }

    public abstract boolean isRenderPageBorders();

    public abstract boolean isRenderTileBorders();

    public abstract boolean isRenderLabelBorders();

    @AutoValue.Builder
    @ToBePublicApi
    public abstract static class Builder {
        @ToBePublicApi
        public abstract Builder renderPageBorders(boolean renderPageBorders);

        public abstract Builder renderTileBorders(boolean renderTileBorders);

        public abstract Builder renderLabelBorders(boolean renderLabelBorders);

        public abstract DocumentRenderOptions build();
    }


    /**
     * Creates a new instance of a {@link TiledPaper.Builder}
     *
     * @return new {@link TiledPaper.Builder} instance
     */
    public static Builder builder() {
        Builder builder = new AutoValue_DocumentRenderOptions.Builder();
        builder.renderPageBorders(false);
        builder.renderTileBorders(false);
        builder.renderLabelBorders(false);
        return builder;
    }
}
