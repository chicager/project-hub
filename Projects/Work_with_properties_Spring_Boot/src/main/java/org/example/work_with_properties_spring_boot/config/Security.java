package org.example.work_with_properties_spring_boot.config;

import lombok.Data;

import java.util.List;

@Data
public class Security {
    private String apiKey;
    private List<String> allowedOrigins;
}
