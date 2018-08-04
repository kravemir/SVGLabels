package org.kravemir.svg.labels.tool.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import java.util.Map;
import java.util.Optional;

@AutoValue
public abstract class ReferringLabelGroup {

    @AutoValue
    @JsonDeserialize(builder = AutoValue_ReferringLabelGroup_Instance.Builder.class)
    public static abstract class Instance {

        private static final int FILL_PAGE = 0;

        public abstract int count();

        public abstract Optional<Map<String, String>> instanceContent();
        public abstract Optional<String> instanceContentRef();

        public boolean shouldFillPage() {
            return count() == FILL_PAGE;
        }

        @AutoValue.Builder
        @JsonPOJOBuilder(withPrefix = "")
        public abstract static class Builder {

            public abstract Builder count(int count);

            public Builder fillPage() {
                count(FILL_PAGE);
                return this;
            }

            public abstract Builder instanceContent(Map<String, String> instanceContent);
            public abstract Builder instanceContentRef(String instanceContentRef);

            public abstract Instance build();
        }

        public static Builder builder() {
            Builder builder = new AutoValue_ReferringLabelGroup_Instance.Builder();
            return builder;
        }

    }
}
