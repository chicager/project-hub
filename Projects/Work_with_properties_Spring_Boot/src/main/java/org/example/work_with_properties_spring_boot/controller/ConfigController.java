package org.example.work_with_properties_spring_boot.controller;

import lombok.RequiredArgsConstructor;
import org.example.work_with_properties_spring_boot.config.AppProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ConfigController {

    private final AppProperties appProperties;

    @GetMapping("/info")
    public AppProperties getConfig() {
        return appProperties;
    }
}
