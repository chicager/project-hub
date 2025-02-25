package org.example.work_with_properties_spring_boot.config.advanced;

import lombok.Data;

import java.util.List;

@Data
public class RetryConfig {
    private int maxAttempts;
    private List<Integer> delays;
}
