package com.example.greeter.formatter;

import com.example.greeter.format.GreetingFormat;
import com.example.greeter.model.GreetingResponse;

import java.time.format.DateTimeFormatter;

public class HtmlFormatter implements GreetingFormatter {
    private final DateTimeFormatter dateTimeFormatter;
    private final String wrapperClass;
    private final String messageClass;
    private final String metadataClass;

    public HtmlFormatter(String dateTimePattern, String wrapperClass, String messageClass, String metadataClass) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
        this.wrapperClass = wrapperClass;
        this.messageClass = messageClass;
        this.metadataClass = metadataClass;
    }

    @Override
    public String format(GreetingResponse response) {
        return String.format("""
            <div class="%s">
                <p class="%s">%s</p>
                <small class="%s">%s</small>
                <small class="%s">%s</small>
            </div>
            """,
                wrapperClass,
                messageClass, response.getMessage(),
                metadataClass, response.getTimestamp().format(dateTimeFormatter),
                metadataClass, response.getLocale().getDisplayLanguage()
        );
    }

    @Override
    public GreetingFormat getFormat() {
        return GreetingFormat.HTML;
    }
}
