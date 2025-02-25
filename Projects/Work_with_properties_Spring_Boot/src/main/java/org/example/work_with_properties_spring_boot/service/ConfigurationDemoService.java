package org.example.work_with_properties_spring_boot.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.work_with_properties_spring_boot.config.AppProperties;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigurationDemoService {

    private final AppProperties appProperties;

    @PostConstruct
    public void init() {
        log.info("Демонстрация конфигурации при инициализации бина:");
        demonstrateConfig();
    }

    public void demonstrateConfig() {
        // Базовые свойства
        log.info("App Name: {}", appProperties.getName());
        log.info("Version: {}", appProperties.getVersion());

        // Работа с Map свойствами
        log.info("\nEndpoints:");
        appProperties.getEndpoints().getEndpoints()
                .forEach((key, value) -> log.info("{} -> {}", key, value));

        // Работа с feature flags
        log.info("\nFeature Flags:");
        appProperties.getFeatureFlags().getFeatureFlags()
                .forEach((feature, enabled) -> log.info("{}: {}", feature, enabled));

        // Работа со списками
        log.info("\nRetry Delays:");
        appProperties.getRetryConfig().getDelays()
                .forEach(delay -> log.info("Delay: {}ms", delay));

        // Работа с вложенными Map
        log.info("\nCache TTL Settings:");
        appProperties.getCache().getTtlSettings()
                .forEach((key, ttl) -> log.info("{} -> {}s", key, ttl));

        // Работа со сложными вложенными структурами
        log.info("\nCORS Settings:");
        appProperties.getCors().getSettings()
                .forEach((origin, config) -> {
                    log.info("Origin: {}", origin);
                    log.info("  Methods: {}", config.getAllowedMethods());
                    log.info("  Max Age: {}", config.getMaxAge());
                });

        // Работа с environment-specific настройками
        log.info("\nEnvironment Settings:");
        appProperties.getEnvironmentSpecific().getEnvironmentSpecific()
                .forEach((env, settings) -> {
                    log.info("Environment: {}", env);
                    log.info("  Logging Level: {}", settings.getLoggingLevel());
                    log.info("  Mock Services: {}", settings.isMockServices());
                });
    }
}
