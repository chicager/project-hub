package com.example.greeter.formatter;

import com.example.greeter.format.GreetingFormat;
import com.example.greeter.model.GreetingResponse;

import java.time.format.DateTimeFormatter;

public class JsonFormatter implements GreetingFormatter {
    private final DateTimeFormatter dateTimeFormatter;
    private final boolean includeMetadata;
    private final boolean prettyPrint;

    public JsonFormatter(String dateTimePattern, boolean includeMetadata, boolean prettyPrint) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
        this.includeMetadata = includeMetadata;
        this.prettyPrint = prettyPrint;
    }

    @Override
    public String format(GreetingResponse response) {
        String template = prettyPrint ?
                """
                {
                    "message": "%s"%s
                }
                """ :
                """
                {"message":"%s"%s}
                """;

        String metadata = includeMetadata ? String.format(
                prettyPrint ?
                        ",\n    \"timestamp\": \"%s\",\n    \"locale\": \"%s\"" :
                        ",\"timestamp\":\"%s\",\"locale\":\"%s\"",
                response.getTimestamp().format(dateTimeFormatter),
                response.getLocale().getDisplayLanguage()
        ) : "";

        return String.format(template, response.getMessage(), metadata);
    }

    @Override
    public GreetingFormat getFormat() {
        return GreetingFormat.JSON;
    }
}
