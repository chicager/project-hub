package org.example.exceptions_spring_boot.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions_spring_boot.model.ErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.stream.Collectors;

/*
@RestControllerAdvice - Это специальная аннотация, которая позволяет обрабатывать исключения глобально для всего приложения.
По сути, это комбинация аннотаций @ControllerAdvice и @ResponseBody.
@RestControllerAdvice перехватывает исключения со всей цепочки вызовов, которая начинается с контроллера.
То есть неважно, где именно произошла ошибка - в контроллере, сервисе, репозитории или любом
другом компоненте, вызванном в процессе обработки HTTP-запроса.
Что она делает:
  Перехватывает исключения, которые возникают в любом месте приложения
  Позволяет обрабатывать их централизованно в одном месте
  Автоматически преобразует возвращаемые объекты в JSON/XML формат (благодаря части @ResponseBody)
Преимущества использования:
  Единая точка обработки всех исключений
  Консистентный формат ответа при ошибках
  Уменьшение дублирования кода
  Улучшение поддержки и читаемости кода

Без @RestControllerAdvice пришлось бы обрабатывать все исключения ниже в каждом контроллере отдельно.
С этой аннотацией Spring автоматически направляет все исключения в этот обработчик.

Это очень важная часть архитектуры обработки ошибок в Spring-приложении,
которая позволяет централизованно и элегантно обрабатывать все исключения.

В приложении может быть несколько классов с аннотацией @RestControllerAdvice
Множественные обработчики:
  Вы можете иметь несколько классов с @RestControllerAdvice
  Каждый такой класс может отвечать за обработку разных типов исключений или разных частей приложения
Как указать область действия:
   // Для конкретного пакета
   @RestControllerAdvice("com.example.controllers")
   // Для конкретных классов
   @RestControllerAdvice(assignableTypes = {UserController.class, OrderController.class})
   // Для контроллеров с определенной аннотацией
   @RestControllerAdvice(annotations = RestController.class)

Если несколько обработчиков могут обработать одно и то же исключение, Spring использует более специфичный.

Такой подход позволяет организовать обработку исключений модульно и поддерживать чистоту кода.
Однако на практике часто достаточно одного глобального обработчика,
если логика обработки ошибок не сильно различается для разных частей приложения.
*/
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /*
    MessageSource - это интерфейс Spring Framework для поддержки интернационализации (i18n).
    Он используется для получения сообщений (текстов) из properties-файлов на разных языках.
    Структура файлов с сообщениями:
      src/main/resources/
        ├── messages.properties      # Сообщения по умолчанию
        ├── messages_en.properties   # Английские сообщения
        ├── messages_ru.properties   # Русские сообщения
        └── messages_es.properties   # Испанские сообщения
    Содержимое файлов:
      # messages_en.properties
      user.not.found=User with id {0} not found
      validation.error=Validation error: {0}
      internal.error=Internal server error occurred

      # messages_ru.properties
      user.not.found=Пользователь с id {0} не найден
      validation.error=Ошибка валидации: {0}
      internal.error=Произошла внутренняя ошибка сервера
    */
    private final MessageSource messageSource;

    /*
    Аннотация @ExceptionHandler указывает, какой тип исключения будет обрабатывать данный метод.
    Можно обрабатывать несколько типов исключений одним методом:
      @ExceptionHandler({UserNotFoundException.class, UserNotActiveException.class})
    Приоритет обработки:
      Spring сначала ищет самый специфичный обработчик для возникшего исключения
      Если точное совпадение не найдено, будет использован обработчик родительского класса исключения
        // Более специфичный обработчик
        @ExceptionHandler(UserNotFoundException.class)
        // Более общий обработчик
        @ExceptionHandler(Exception.class)  // Будет вызван только если нет более специфичного обработчика
    */
    /*
    ResponseEntity - это класс Spring Framework, который представляет собой полный HTTP-ответ,
    включая статус, заголовки и тело ответа.
    Основное назначение:
      Позволяет полностью настроить HTTP-ответ
      Дает контроль над статусом ответа, заголовками и телом
      Часто используется в REST API
    ResponseEntity особенно полезен, когда вам нужно:
      Вернуть специфический HTTP-статус
      Добавить пользовательские заголовки
      Обеспечить типобезопасность ответа
      Создать единообразный формат ответов API
      Обработать ошибки с правильными HTTP-статусами
    Основные способы использования:
      // Простой ответ со статусом
      return new ResponseEntity<>(HttpStatus.OK);  // Пустой ответ с кодом 200
      // Ответ с телом и статусом
      return new ResponseEntity<>(user, HttpStatus.OK);  // Ответ с данными и кодом 200
      // Ответ с заголовками
      HttpHeaders headers = new HttpHeaders();
      headers.add("Custom-Header", "value");
      return new ResponseEntity<>(user, headers, HttpStatus.OK);
    Удобные статические методы:
      // Успешные ответы
      return ResponseEntity.ok(user);                    // 200 OK
      return ResponseEntity.created(location).build();   // 201 Created
      // Ответы с ошибками
      return ResponseEntity.notFound().build();          // 404 Not Found
      return ResponseEntity.badRequest().body(error);    // 400 Bad Request
    */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        log.warn("User not found exception. Key: {}, Args: {}", ex.getMessageKey(), ex.getArgs());
        String message = messageSource.getMessage(ex.getMessageKey(), ex.getArgs(), Locale.getDefault());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .code("USER_NOT_FOUND")
                .path(request.getDescription(false))
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserEmailExistsException(UserEmailExistsException ex, WebRequest request) {
        log.error("User email exists exception", ex);
        String message = messageSource.getMessage(ex.getMessageKey(), ex.getArgs(), Locale.getDefault());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .code("USER_EMAIL_EXISTS")
                .path(request.getDescription(false))
                .build();

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("Validation exception", ex);
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(messageSource.getMessage("validation.error", new Object[]{message}, Locale.getDefault()))
                .code("VALIDATION_ERROR")
                .path(request.getDescription(false))
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Unexpected error occurred", ex);
        String path = request.getDescription(false);
        log.error("Error details - Path: {}, Exception type: {}", path, ex.getClass().getName());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(messageSource.getMessage("internal.error", null, Locale.getDefault()))
                .code("INTERNAL_ERROR")
                .path(path)
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
