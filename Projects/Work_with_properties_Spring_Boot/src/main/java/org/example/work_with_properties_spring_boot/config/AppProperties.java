package org.example.work_with_properties_spring_boot.config;

import lombok.Data;
import org.example.work_with_properties_spring_boot.config.advanced.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/*
Аннотация @Component используется в классе AppProperties потому что этот класс является основным классом конфигурации,
который нужно зарегистрировать как bean в контексте Spring для последующего
внедрения в другие компоненты (сервисы, контроллеры).
Другие классы (Features, Security, Database, Notification) являются просто POJO-классами (Plain Old Java Objects),
которые используются как части конфигурации внутри AppProperties. Они не нуждаются в регистрации
как отдельные бины Spring, поскольку:
  Они создаются и управляются автоматически через класс AppProperties
  К ним нет прямого доступа из других компонентов - доступ всегда идет через класс AppProperties

Если бы мы пометили все классы конфигурации аннотацией @Component, это привело бы к:
  Ненужному созданию дополнительных бинов
  Возможным конфликтам при автоматической конфигурации
  Нарушению инкапсуляции конфигурационных свойств
Поэтому @Component используется только для основного класса конфигурации AppProperties,
который служит единой точкой доступа ко всем настройкам приложения.
*/

/*
Аннотация @Validated нужна именно в классе AppProperties, а не в Features, потому что:
  1. @Validated указывает Spring, что нужно активировать валидацию для этого бина и всех его вложенных объектов
  2. Features не является самостоятельным бином Spring, он является частью AppProperties
  3. В Features мы только объявляем правила валидации (@NotNull, @Min), но сама валидация запускается на уровне AppProperties
Это работает так:
  Spring создает бин AppProperties
  Видит аннотацию @Validated
  При заполнении свойств из application.yml проверяет все поля, включая вложенные объекты
  Если в Features нарушены правила валидации (например, enabled = null или maxItems < 1), то Spring выбросит исключение
Если бы мы добавили @Validated в класс Features, это не дало бы никакого эффекта, так как этот класс не является
бином Spring и не проходит через процесс валидации самостоятельно.
*/

/*
Аннотация @EnableConfigurationProperties необходима для работы с @ConfigurationProperties, если мы не используем @Component.
В Spring Boot есть два способа работы с конфигурационными свойствами:

  1. Использовать @Component + @ConfigurationProperties:
    @Component
    @ConfigurationProperties(prefix = "app")
    public class AppProperties { ... }

  2. Использовать @EnableConfigurationProperties + @ConfigurationProperties:
    @EnableConfigurationProperties(AppProperties.class)
    @SpringBootApplication
    public class WorkWithPropertiesSpringBootApplication { ... }

    @ConfigurationProperties(prefix = "app")
    public class AppProperties { ... }

Хотя @SpringBootApplication действительно включает некоторую автоконфигурацию,
она не включает автоматически все классы с @ConfigurationProperties.
Поэтому у нас есть выбор:
  1. Либо оставить @Component в AppProperties
  2. Либо использовать @EnableConfigurationProperties в главном классе
Второй подход (@EnableConfigurationProperties) считается более предпочтительным, потому что:
  Явно показывает, что класс предназначен для конфигурации
  Отделяет конфигурационные классы от обычных компонентов
  Позволяет использовать конфигурационные классы в автоконфигурации
*/

@Data
@Validated //включает валидацию полей класса
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;
    private String version;
    private Features features;
    private Security security;
    private Database database;
    private Notification notification;
    private EndpointConfig endpoints;
    private FeatureFlags featureFlags;
    private RetryConfig retryConfig;
    private CacheConfig cache;
    private CorsSettings cors;
    private EnvironmentConfig environmentSpecific;
}
