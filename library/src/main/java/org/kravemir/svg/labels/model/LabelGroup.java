package org.kravemir.svg.labels.model;

import com.google.auto.value.AutoValue;
import org.kravemir.svg.labels.annotations.ToBePublicApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The <code>LabelGroup</code> is a value class, and a group of labels.
 *
 * Use a {@link Builder} to construct instance.
 */
// TODO: support different types of units
@AutoValue
@ToBePublicApi
public abstract class LabelGroup {

    @AutoValue
    @ToBePublicApi
    public static abstract class Instance {

        private static final int FILL_PAGE = 0;

        @ToBePublicApi
        public abstract int getCount();

        @ToBePublicApi
        public abstract Map<String, String> getInstanceContent();

        @ToBePublicApi
        public boolean shouldFillPage() {
            return getCount() == FILL_PAGE;
        }

        @AutoValue.Builder
        @ToBePublicApi
        public abstract static class Builder {

            @ToBePublicApi
            public abstract Builder setCount(int count);

            @ToBePublicApi
            public Builder fillPage() {
                setCount(FILL_PAGE);
                return this;
            }

            @ToBePublicApi
            public abstract Builder setInstanceContent(Map<String, String> instanceContent);

            @ToBePublicApi
            public abstract Instance build();
        }


        /**
         * Creates a new instance of a {@link LabelGroup.Builder}
         *
         * @return new {@link LabelGroup.Builder} instance
         */
        @ToBePublicApi
        public static Builder builder() {
            Builder builder = new AutoValue_LabelGroup_Instance.Builder();
            builder.setInstanceContent(Collections.emptyMap());
            return builder;
        }

    }

    protected LabelGroup() {
    }

    @ToBePublicApi
    public abstract String getTemplate();

    @ToBePublicApi
    public abstract LabelTemplateDescriptor getTemplateDescriptor();

    @ToBePublicApi
    public abstract List<Instance> getInstances();

    @AutoValue.Builder
    @ToBePublicApi
    public abstract static class Builder {

        @ToBePublicApi
        public abstract Builder setTemplate(String template);

        @ToBePublicApi
        public abstract Builder setTemplateDescriptor(LabelTemplateDescriptor descriptor);

        @ToBePublicApi
        public abstract Builder setInstances(List<Instance> instances);

        @ToBePublicApi
        public final Builder setInstances(Instance ...instances) {
            return setInstances(Arrays.asList(instances));
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
        return new AutoValue_LabelGroup.Builder().setTemplateDescriptor(LabelTemplateDescriptor.EMPTY);
    }
}
