package org.kravemir.svg.labels;

public class LabelGroup {

    private static final int FILL_PAGE = 0;

    private String template;
    private int count;

    private LabelGroup(Builder builder) {
        this.template = builder.template;
        this.count = builder.count;
    }

    public String getTemplate() {
        return template;
    }

    public int getCount() {
        return count;
    }

    public boolean shouldFillPage() {
        return count == FILL_PAGE;
    }

    public static class Builder {
        private String template;
        private int count;

        public Builder withTemplate(String template) {
            this.template = template;
            return this;
        }

        public Builder withCount(int count) {
            // TODO: check count > 0
            this.count = count;
            return this;
        }

        public Builder fillPage() {
            this.count = FILL_PAGE;
            return this;
        }

        public LabelGroup build() {
            return new LabelGroup(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
