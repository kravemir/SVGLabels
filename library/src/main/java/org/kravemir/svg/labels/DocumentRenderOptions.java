package org.kravemir.svg.labels;

/**
 * The <code>DocumentRenderOptions</code> is a value class, and contains rendering options.
 *
 * Use a {@link Builder} to construct instance.
 */
public class DocumentRenderOptions {

    private boolean renderPageBorders;
    private boolean renderTileBorders;
    private boolean renderLabelBorders;

    // TODO: consistency of construction by Builder
    private DocumentRenderOptions() {
    }

    public boolean isRenderPageBorders() {
        return renderPageBorders;
    }

    // TODO: this should probably be in RenderLabelOptions
    public boolean isRenderTileBorders() {
        return renderTileBorders;
    }

    // TODO: this should probably be in RenderLabelOptions
    public boolean isRenderLabelBorders() {
        return renderLabelBorders;
    }

    public static class Builder {
        private boolean renderPageBorders;
        private boolean renderTileBorders;
        private boolean renderLabelBorders;

        public Builder withRenderPageBorders(boolean renderPageBorders) {
            this.renderPageBorders = renderPageBorders;
            return this;
        }

        public Builder withRenderTileBorders(boolean renderTileBorders) {
            this.renderTileBorders = renderTileBorders;
            return this;
        }

        public Builder withRenderLabelBorders(boolean renderLabelBorders) {
            this.renderLabelBorders = renderLabelBorders;
            return this;
        }

        public DocumentRenderOptions build() {
            DocumentRenderOptions options = new DocumentRenderOptions();
            options.renderPageBorders = renderPageBorders;
            options.renderLabelBorders = renderLabelBorders;
            options.renderTileBorders = renderTileBorders;
            return options;
        }
    }


    /**
     * Creates a new instance of a {@link TiledPaper.Builder}
     *
     * @return new {@link TiledPaper.Builder} instance
     */
    public static Builder builder() {
        return new Builder();
    }
}
