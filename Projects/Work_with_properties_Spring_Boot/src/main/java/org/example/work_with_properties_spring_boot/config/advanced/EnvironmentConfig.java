package org.example.work_with_properties_spring_boot.config.advanced;

import lombok.Data;

import java.util.Map;

@Data
public class EnvironmentConfig {
    private Map<String, EnvSettings> environmentSpecific;

    @Data
    public static class EnvSettings {
        private String loggingLevel;
        private boolean mockServices;
    }
}
