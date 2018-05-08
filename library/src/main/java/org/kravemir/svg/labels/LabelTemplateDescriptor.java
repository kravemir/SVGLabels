package org.kravemir.svg.labels;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
@JsonAutoDetect
@JsonDeserialize(builder = AutoValue_LabelTemplateDescriptor.Builder.class)
public abstract class LabelTemplateDescriptor {

    @AutoValue
    @JsonAutoDetect
    @JsonDeserialize(builder = AutoValue_LabelTemplateDescriptor_Attribute.Builder.class)
    public abstract static class Attribute {
        public abstract String getKey();


        @AutoValue.Builder
        @JsonPOJOBuilder(withPrefix = "")
        public abstract static class Builder {
            public abstract Builder key(String key);

            public abstract Attribute build();
        }
    }

    @AutoValue
    @JsonAutoDetect
    @JsonDeserialize(builder = AutoValue_LabelTemplateDescriptor_ContentReplaceRule.Builder.class)
    public abstract static class ContentReplaceRule {
        public abstract String getValue();
        public abstract String getElementXPath();


        @AutoValue.Builder
        @JsonPOJOBuilder(withPrefix = "")
        public abstract static class Builder {
            public abstract Builder value(String value);
            public abstract Builder elementXPath(String elementXPath);

            public abstract ContentReplaceRule build();
        }
    }


    public abstract List<Attribute> getAttributes();
    public abstract List<ContentReplaceRule> getContentReplaceRules();


    @AutoValue.Builder
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class Builder {
        public abstract Builder attributes(List<Attribute> attributes);
        public abstract Builder contentReplaceRules(List<ContentReplaceRule> contentReplaceRules);

        public abstract LabelTemplateDescriptor build();
    }
}
