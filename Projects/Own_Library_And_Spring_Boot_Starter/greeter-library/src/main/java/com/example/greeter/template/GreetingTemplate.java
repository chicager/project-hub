package com.example.greeter.template;

import java.util.Locale;

public interface GreetingTemplate {
    String generateMessage(String name, Locale locale);
}
