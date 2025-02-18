package org.example.interceptors_spring_boot.config;

import org.example.interceptors_spring_boot.model.Task;
import org.example.interceptors_spring_boot.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/*
Этот код используется для предварительной загрузки тестовых данных.
Выполняется только в dev-окружении (благодаря @Profile("dev")).
Удобно для разработки и тестирования.

Порядок выполнения:
  Spring Boot запускается
  Проверяет активный профиль
  Если профиль "dev" - выполняет этот код
  Создает записи в базе данных
  Продолжает запуск приложения

Практическое применение:
  Удобно для демо-данных
  Для тестового окружения
  Для начальной настройки приложения
  Для создания тестовых сценариев

Это типичный паттерн для инициализации данных в Spring Boot приложениях во время разработки.
*/

@Profile("dev") // Код ниже будет выполняться только если в application.yml активен профиль "dev"
@Configuration
public class DataLoaderConfig {

    //CommandLineRunner - интерфейс Spring Boot, который выполняется сразу после старта приложения
    //TaskRepository repository - Spring автоматически внедрит репозиторий как параметр

    //null в конструкторе Task - ID будет сгенерирован автоматически
    @Bean
    CommandLineRunner initDatabase(TaskRepository repository) {
        return args -> {
            repository.save(new Task(null, "Изучить Spring Boot", "Разобраться с основами Spring Boot", false));
            repository.save(new Task(null, "Изучить Spring Security", "Понять как работает аутентификация", false));
            repository.save(new Task(null, "Написать REST API", "Создать CRUD операции для задач", true));
            repository.save(new Task(null, "Изучить Spring Data JPA", "Разобраться с репозиториями и сущностями", false));
            repository.save(new Task(null, "Настроить логирование", "Добавить логирование через interceptor", true));
        };
    }
}
