package org.kravemir.svg.labels.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
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
    @JsonDeserialize(builder = AutoValue_LabelGroup_Instance.Builder.class)
    public static abstract class Instance {

        private static final int FILL_PAGE = 0;

        @ToBePublicApi
        public abstract int count();

        @ToBePublicApi
        public abstract Map<String, String> instanceContent();

        @ToBePublicApi
        public boolean shouldFillPage() {
            return count() == FILL_PAGE;
        }

        @AutoValue.Builder
        @ToBePublicApi
        @JsonPOJOBuilder(withPrefix = "")
        public abstract static class Builder {

            @ToBePublicApi
            public abstract Builder count(int count);

            @ToBePublicApi
            public Builder fillPage() {
                count(FILL_PAGE);
                return this;
            }

            @ToBePublicApi
            public abstract Builder instanceContent(Map<String, String> instanceContent);

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
            builder.instanceContent(Collections.emptyMap());
            return builder;
        }

    }

    protected LabelGroup() {
    }

    @ToBePublicApi
    public abstract String template();

    @ToBePublicApi
    public abstract LabelTemplateDescriptor templateDescriptor();

    @ToBePublicApi
    public abstract List<Instance> instances();

    @AutoValue.Builder
    @ToBePublicApi
    public abstract static class Builder {

        @ToBePublicApi
        public abstract Builder template(String template);

        @ToBePublicApi
        public abstract Builder templateDescriptor(LabelTemplateDescriptor descriptor);

        @ToBePublicApi
        public abstract Builder instances(List<Instance> instances);

        @ToBePublicApi
        public final Builder instances(Instance ...instances) {
            return instances(Arrays.asList(instances));
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
        return new AutoValue_LabelGroup.Builder().templateDescriptor(LabelTemplateDescriptor.EMPTY);
    }
}
