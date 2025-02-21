package com.example.greeter.template;

import java.util.Locale;
import java.util.ResourceBundle;

public class SimpleGreetingTemplate implements GreetingTemplate {
    private final String prefix;
    private final String suffix;

    public SimpleGreetingTemplate(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public String generateMessage(String name, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("greetings", locale);
        String localizedPrefix = bundle.getString("greeting.prefix");
        return String.format("%s %s %s", localizedPrefix, name, suffix);
    }
}
