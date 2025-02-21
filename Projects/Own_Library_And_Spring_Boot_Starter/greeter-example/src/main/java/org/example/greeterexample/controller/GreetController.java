package org.example.greeterexample.controller;

import com.example.greeter.Greeter;
import com.example.greeter.format.GreetingFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class GreetController {

    private final Greeter greeter;

    @GetMapping("/greet")
    public String greet(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lang,
            @RequestParam(required = false) GreetingFormat format
    ) {
        Locale locale = lang != null ? new Locale(lang) : Locale.getDefault();
        return greeter.greet(name, locale, format != null ? format : GreetingFormat.TEXT);
    }
}
