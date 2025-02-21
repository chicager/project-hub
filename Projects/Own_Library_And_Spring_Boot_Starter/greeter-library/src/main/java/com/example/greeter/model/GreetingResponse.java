package com.example.greeter.model;

import java.time.LocalDateTime;
import java.util.Locale;

public class GreetingResponse {
    private final String message;
    private final LocalDateTime timestamp;
    private final Locale locale;

    public GreetingResponse(String message, LocalDateTime timestamp, Locale locale) {
        this.message = message;
        this.timestamp = timestamp;
        this.locale = locale;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Locale getLocale() {
        return locale;
    }
}
