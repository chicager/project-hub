package org.example.exceptions_spring_boot.controller;

import org.example.exceptions_spring_boot.model.ErrorResponse;
import org.example.exceptions_spring_boot.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/*
@SpringBootTest - Указывает, что это интеграционный тест Spring Boot приложения.
Когда мы используем @SpringBootTest, Spring Boot запускает полноценное приложение для тестирования:
  Поднимается весь контекст Spring приложения
  Запускается встроенный веб-сервер (например, Tomcat) на случайном порту
  Поднимается база данных:
    Обычно для тестов используется встроенная in-memory база данных (например, H2)
    Или можно настроить тестовую базу данных отдельно через конфигурацию
Это отличается от модульных тестов (unit tests), где тестируется только один компонент в изоляции.
В случае с @SpringBootTest мы проводим полноценное интеграционное тестирование:

WebEnvironment.RANDOM_PORT - означает, что для теста будет запущен реальный веб-сервер на случайном свободном порту
*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    //Для выполнения HTTP-запросов используется TestRestTemplate - специальный класс Spring для тестирования REST API
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateValidUser() {
        // given
        var user = User.builder()
                .name("Тест Тестов")
                .email("test@example.com")
                .age(25)
                .build();

        /*
        Этот запрос идёт через реальный HTTP
        проходит через все слои приложения
        и сохраняет данные в базу
        */
        // when
        ResponseEntity<User> response = restTemplate.postForEntity("/api/users", user, User.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Тест Тестов");
        assertThat(response.getBody().getEmail()).isEqualTo("test@example.com");
        assertThat(response.getBody().getAge()).isEqualTo(25);
    }

    @Test
    void shouldNotCreateUserWithExistingEmail() {
        // given
        var user1 = User.builder()
                .name("Тест Тестов")
                .email("duplicate@example.com")
                .age(25)
                .build();

        var user2 = User.builder()
                .name("Другой Тестов")
                .email("duplicate@example.com")
                .age(30)
                .build();

        // when
        restTemplate.postForEntity("/api/users", user1, User.class);
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity("/api/users", user2, ErrorResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("USER_EMAIL_EXISTS");
        assertThat(response.getBody().getMessage()).contains("duplicate@example.com");
    }

    @Test
    void shouldNotCreateUserWithInvalidAge() {
        // given
        var user = User.builder()
                .name("Тест Тестов")
                .email("young@example.com")
                .age(16)
                .build();

        // when
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity("/api/users", user, ErrorResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains("18");
    }

    @Test
    void shouldGetExistingUser() {
        // given
        var user = User.builder()
                .name("Тест Тестов")
                .email("get@example.com")
                .age(25)
                .build();
        ResponseEntity<User> createResponse = restTemplate.postForEntity("/api/users", user, User.class);
        Long userId = createResponse.getBody().getId();

        // when
        ResponseEntity<User> response = restTemplate.getForEntity("/api/users/" + userId, User.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(userId);
        assertThat(response.getBody().getName()).isEqualTo("Тест Тестов");
    }

    @Test
    void shouldNotGetNonExistingUser() {
        // when
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity("/api/users/999", ErrorResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("USER_NOT_FOUND");
        assertThat(response.getBody().getMessage()).contains("999");
    }
}