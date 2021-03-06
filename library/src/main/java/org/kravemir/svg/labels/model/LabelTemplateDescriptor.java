package org.kravemir.svg.labels.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@AutoValue
@JsonAutoDetect
@JsonDeserialize(builder = AutoValue_LabelTemplateDescriptor.Builder.class)
public abstract class LabelTemplateDescriptor {

    public static final LabelTemplateDescriptor EMPTY;

    @AutoValue
    @JsonAutoDetect
    @JsonDeserialize(builder = AutoValue_LabelTemplateDescriptor_Attribute.Builder.class)
    public abstract static class Attribute {
        public abstract String key();


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
        // TODO: add JSON-ignored xpath as javax.xml.xpath.XPath, tie together with string xpath property

        public abstract String value();
        public abstract String elementXPath();

        @Nullable
        @JsonProperty("if")
        public abstract String ifCondition();


        @AutoValue.Builder
        @JsonPOJOBuilder(withPrefix = "")
        public abstract static class Builder {
            // TODO: add xpath validation

            public abstract Builder value(String value);
            public abstract Builder elementXPath(String elementXPath);

            @JsonProperty("if")
            public abstract Builder ifCondition(String ifCondition);

            public abstract ContentReplaceRule build();
        }

    }


    public abstract List<Attribute> attributes();
    public abstract List<ContentReplaceRule> contentReplaceRules();


    @AutoValue.Builder
    @JsonPOJOBuilder(withPrefix = "")
    public abstract static class Builder {
        public abstract Builder attributes(List<Attribute> attributes);
        public abstract Builder contentReplaceRules(List<ContentReplaceRule> contentReplaceRules);

        public abstract LabelTemplateDescriptor build();
    }

    static {
        Builder emptyDescriptorBuilder = new AutoValue_LabelTemplateDescriptor.Builder();
        emptyDescriptorBuilder.attributes(Collections.emptyList());
        emptyDescriptorBuilder.contentReplaceRules(Collections.emptyList());
        EMPTY = emptyDescriptorBuilder.build();
    }
}
