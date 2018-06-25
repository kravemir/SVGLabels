package org.kravemir.svg.labels.model;

import com.google.auto.value.AutoValue;
import org.kravemir.svg.labels.annotations.ToBePublicApi;

/**
 * The <code>LabelGroup</code> is a value class, and a group of labels.
 *
 * Use a {@link Builder} to construct instance.
 */
// TODO: support different types of units
@AutoValue
@ToBePublicApi
public abstract class LabelGroup {

    private static final int FILL_PAGE = 0;

    protected LabelGroup() {
    }

    @ToBePublicApi
    public abstract String getTemplate();

    @ToBePublicApi
    public abstract int getCount();

    @ToBePublicApi
    public boolean shouldFillPage() {
        return getCount() == FILL_PAGE;
    }

    @AutoValue.Builder
    @ToBePublicApi
    public abstract static class Builder {

        @ToBePublicApi
        public abstract Builder setTemplate(String template);

        @ToBePublicApi
        public abstract Builder setCount(int count);

        @ToBePublicApi
        public Builder fillPage() {
            setCount(FILL_PAGE);
            return this;
        }

        @ToBePublicApi
        public abstract LabelGroup build();
    }


    /**
     * Creates a new instance of a {@link Builder}
     *
     * @return new {@link Builder} instance
     */
    @ToBePublicApi
    public static Builder builder() {
        return new AutoValue_LabelGroup.Builder();
    }
}
