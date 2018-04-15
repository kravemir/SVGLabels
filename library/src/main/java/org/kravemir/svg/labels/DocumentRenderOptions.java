package org.kravemir.svg.labels;

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

    @ToBePublicApi
    public abstract boolean isRenderPageBorders();

    // TODO: this should probably be in RenderLabelOptions
    public abstract boolean isRenderTileBorders();

    // TODO: this should probably be in RenderLabelOptions
    public abstract boolean isRenderLabelBorders();

    @AutoValue.Builder
    @ToBePublicApi
    public abstract static class Builder {
        @ToBePublicApi
        public abstract Builder setRenderPageBorders(boolean renderPageBorders);

        public abstract Builder setRenderTileBorders(boolean renderTileBorders);

        public abstract Builder setRenderLabelBorders(boolean renderLabelBorders);

        public abstract DocumentRenderOptions build();
    }


    /**
     * Creates a new instance of a {@link TiledPaper.Builder}
     *
     * @return new {@link TiledPaper.Builder} instance
     */
    public static Builder builder() {
        return new AutoValue_DocumentRenderOptions.Builder();
    }
}
