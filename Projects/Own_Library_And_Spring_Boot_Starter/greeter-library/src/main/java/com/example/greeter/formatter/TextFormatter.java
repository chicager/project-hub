package com.example.greeter.formatter;

import com.example.greeter.format.GreetingFormat;
import com.example.greeter.model.GreetingResponse;

import java.time.format.DateTimeFormatter;

public class TextFormatter implements GreetingFormatter {
    private final DateTimeFormatter dateTimeFormatter;

    public TextFormatter(String dateTimePattern) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
    }

    @Override
    public String format(GreetingResponse response) {
        return String.format("%s [%s] (%s)",
                response.getMessage(),
                response.getTimestamp().format(dateTimeFormatter),
                response.getLocale().getDisplayLanguage()
        );
    }

    @Override
    public GreetingFormat getFormat() {
        return GreetingFormat.TEXT;
    }
}
