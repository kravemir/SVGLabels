package org.kravemir.svg.labels.model;

import com.google.auto.value.AutoValue;
import org.kravemir.svg.labels.annotations.ToBePublicApi;

import java.util.Optional;

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
        public abstract Builder setRenderPageBorders(boolean renderPageBorders);

        public abstract Builder setRenderTileBorders(boolean renderTileBorders);

        public abstract Builder setRenderLabelBorders(boolean renderLabelBorders);

        abstract Optional<Boolean> isRenderPageBorders();
        abstract Optional<Boolean> isRenderTileBorders();
        abstract Optional<Boolean> isRenderLabelBorders();

        abstract DocumentRenderOptions autoBuild();

        public final DocumentRenderOptions build() {
            if(!isRenderPageBorders().isPresent())
                setRenderPageBorders(false);
            if(!isRenderTileBorders().isPresent())
                setRenderTileBorders(false);
            if(!isRenderLabelBorders().isPresent())
                setRenderLabelBorders(false);

            return autoBuild();
        }
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
