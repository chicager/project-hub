package org.example.work_with_properties_spring_boot.config.advanced;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CorsSettings {
    private Map<String, CorsConfig> settings;

    @Data
    public static class CorsConfig {
        private List<String> allowedMethods;
        private Integer maxAge;
    }
}
