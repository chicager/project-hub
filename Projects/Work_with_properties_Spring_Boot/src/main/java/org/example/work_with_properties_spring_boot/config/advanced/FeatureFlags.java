package org.example.work_with_properties_spring_boot.config.advanced;

import lombok.Data;

import java.util.Map;

@Data
public class FeatureFlags {
    //Spring Boot сам инициализирует коллекции при биндинге свойств, т.е. не нано инициализировать через new
    private Map<String, Boolean> featureFlags;
}
