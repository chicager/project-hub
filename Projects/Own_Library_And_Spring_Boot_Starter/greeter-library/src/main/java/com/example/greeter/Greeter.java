package com.example.greeter;

import com.example.greeter.format.GreetingFormat;
import com.example.greeter.formatter.GreetingFormatter;
import com.example.greeter.model.GreetingResponse;
import com.example.greeter.template.GreetingTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Greeter {
    private final GreetingTemplate template;
    private final Map<GreetingFormat, GreetingFormatter> formatters;

    public Greeter(GreetingTemplate template, List<GreetingFormatter> formatters) {
        this.template = template;
        this.formatters = formatters.stream()
                .collect(Collectors.toMap(GreetingFormatter::getFormat, Function.identity()));
    }

    public String greet(String name, Locale locale, GreetingFormat format) {
        String message = template.generateMessage(name, locale);
        GreetingResponse response = new GreetingResponse(message, LocalDateTime.now(), locale);

        GreetingFormatter formatter = formatters.get(format);
        if (formatter == null) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }

        return formatter.format(response);
    }

    public String greet(String name) {
        return greet(name, Locale.getDefault(), GreetingFormat.TEXT);
    }
}
