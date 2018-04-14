package org.kravemir.svg.labels;

import org.kravemir.svg.labels.annotations.ToBePublicApi;

/**
 * The <code>LabelGroup</code> is a value class, and a group of labels.
 *
 * Use a {@link Builder} to construct instance.
 */
// TODO: support different types of units
@ToBePublicApi
public class LabelGroup {

    private static final int FILL_PAGE = 0;

    private String template;
    private int count;

    // TODO: consistency of construction by Builder
    private LabelGroup(Builder builder) {
        this.template = builder.template;
        this.count = builder.count;
    }

    @ToBePublicApi
    public String getTemplate() {
        return template;
    }

    @ToBePublicApi
    public int getCount() {
        return count;
    }

    @ToBePublicApi
    public boolean shouldFillPage() {
        return count == FILL_PAGE;
    }

    @ToBePublicApi
    public static class Builder {
        private String template;
        private int count;

        @ToBePublicApi
        public Builder withTemplate(String template) {
            this.template = template;
            return this;
        }

        @ToBePublicApi
        public Builder withCount(int count) {
            // TODO: check count > 0
            this.count = count;
            return this;
        }

        @ToBePublicApi
        public Builder fillPage() {
            this.count = FILL_PAGE;
            return this;
        }

        @ToBePublicApi
        public LabelGroup build() {
            return new LabelGroup(this);
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
