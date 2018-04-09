package org.kravemir.svg.labels.api;

public class DocumentRenderOptions {

    private boolean renderPageBorders;
    private boolean renderTileBorders;
    private boolean renderLabelBorders;

    private DocumentRenderOptions() {
    }

    public boolean isRenderPageBorders() {
        return renderPageBorders;
    }

    public boolean isRenderTileBorders() {
        return renderTileBorders;
    }

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

    public static Builder builder() {
        return new Builder();
    }
}
