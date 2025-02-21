package com.example.greeter.formatter;

import com.example.greeter.format.GreetingFormat;
import com.example.greeter.model.GreetingResponse;

public interface GreetingFormatter {
    String format(GreetingResponse response);
    GreetingFormat getFormat();
}