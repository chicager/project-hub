package org.example.work_with_properties_spring_boot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.work_with_properties_spring_boot.service.ConfigurationDemoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/*
Аннотация @Profile используется для условной регистрации бинов в зависимости от активных профилей.
В Spring Boot, если не указан явно никакой профиль, по умолчанию активируется профиль default.
*/

@Slf4j
@Component
@Profile("!test") // Этот бин не будет создан, если активен профиль "test"
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {
    private final ConfigurationDemoService configurationDemoService;

    @Override
    public void run(String... args) {
        log.info("Приложение запущено, демонстрация конфигурации:");
        configurationDemoService.demonstrateConfig();
    }
}
