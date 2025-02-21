package com.example.greeter.autoconfigure;

import com.example.greeter.format.GreetingFormat;
import jdk.jfr.Description;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "greeter")
public class GreeterProperties {
    @Description("Prefix to use in greetings")
    private String prefix;

    @Description("Suffix to append to greetings")
    private String suffix;

    @Description("Default format for greeting responses (TEXT, HTML, JSON)")
    private GreetingFormat defaultFormat;

    private FormattingProperties formatting = new FormattingProperties();

    //Getters and Setters
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public GreetingFormat getDefaultFormat() {
        return defaultFormat;
    }

    public void setDefaultFormat(GreetingFormat defaultFormat) {
        this.defaultFormat = defaultFormat;
    }

    public FormattingProperties getFormatting() {
        return formatting;
    }

    public void setFormatting(FormattingProperties formatting) {
        this.formatting = formatting;
    }

    public static class FormattingProperties {
        private String datetimePattern;
        private HtmlProperties html = new HtmlProperties();
        private JsonProperties json = new JsonProperties();

        //Getters and Setters
        public String getDatetimePattern() {
            return datetimePattern;
        }

        public void setDatetimePattern(String datetimePattern) {
            this.datetimePattern = datetimePattern;
        }

        public HtmlProperties getHtml() {
            return html;
        }

        public void setHtml(HtmlProperties html) {
            this.html = html;
        }

        public JsonProperties getJson() {
            return json;
        }

        public void setJson(JsonProperties json) {
            this.json = json;
        }
    }

    public static class HtmlProperties {
        private String wrapperClass;
        private String messageClass;
        private String metadataClass;

        //Getters and Setters
        public String getWrapperClass() {
            return wrapperClass;
        }

        public void setWrapperClass(String wrapperClass) {
            this.wrapperClass = wrapperClass;
        }

        public String getMessageClass() {
            return messageClass;
        }

        public void setMessageClass(String messageClass) {
            this.messageClass = messageClass;
        }

        public String getMetadataClass() {
            return metadataClass;
        }

        public void setMetadataClass(String metadataClass) {
            this.metadataClass = metadataClass;
        }
    }

    public static class JsonProperties {
        private Boolean includeMetadata;
        private Boolean prettyPrint;

        //Getters and Setters
        public boolean isIncludeMetadata() {
            return includeMetadata != null && includeMetadata;
        }

        public void setIncludeMetadata(boolean includeMetadata) {
            this.includeMetadata = includeMetadata;
        }

        public boolean isPrettyPrint() {
            return prettyPrint != null && prettyPrint;
        }

        public void setPrettyPrint(boolean prettyPrint) {
            this.prettyPrint = prettyPrint;
        }
    }
}
