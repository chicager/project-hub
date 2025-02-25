package org.example.work_with_properties_spring_boot.config.advanced;

import lombok.Data;

import java.util.Map;

@Data
public class CacheConfig {
    private Map<String, Integer> ttlSettings;
}