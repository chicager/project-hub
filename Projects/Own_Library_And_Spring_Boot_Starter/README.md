# Пример создания своей библиотеки и spring-boot-starter'а на основе нее

------------
## Структура проекта

Проект состоит из трёх модулей:

- **greeter-library**: Основная библиотека с базовым функционалом
- **greeter-spring-boot-starter**: Spring Boot автоконфигурация (стартер)
- **greeter-example**: Демонстрационное приложение

------------

### Greeter Library

Библиотека для создания настраиваемых приветственных сообщений с поддержкой различных форматов вывода и интернационализации.

#### Возможности

- 🌍 Поддержка интернационализации (i18n)
- 🎨 Несколько форматов вывода (TEXT, HTML, JSON)
- ⚙️ Настройка формата по умолчанию

------------

## Быстрый старт

### 1. Добавьте в приложение зависимость (стартер)

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>greeter-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### 2. Настройте приложение (необязательно)

```yaml
greeter:
  prefix: "Hello"
  suffix: "!"
  default-format: TEXT
  formatting:
    datetime-pattern: "yyyy-MM-dd HH:mm:ss"
```

### 3. Используйте в коде

```java
@Autowired
private Greeter greeter;

public String sayHello(String name) {
    return greeter.greet(name);
}
```

## Форматы вывода

### TEXT
GET http://localhost:8080/greet?name=World

```
Привет  World ! [2025-02-23 05:37:13] (русский)
```

### HTML
GET http://localhost:8080/greet?name=World&format=HTML
```html
<div class="greeting">
    <p class="message">Привет  World !</p>
    <small class="metadata">2025-02-23 05:39:05</small>
    <small class="metadata">русский</small>
</div>
```

### JSON
GET http://localhost:8080/greet?name=World&format=JSON

```json
{
  "message": "Привет  World !",
  "timestamp": "2025-02-23 05:39:55",
  "locale": "русский"
}
```

## Конфигурация

### Основные настройки

| Параметр | Описание | Значение по умолчанию |
|----------|----------|----------------------|
| greeter.prefix | Префикс приветствия | "Hello" |
| greeter.suffix | Суффикс приветствия | "!" |
| greeter.default-format | Формат по умолчанию | TEXT |

### Форматирование

```yaml
greeter:
  formatting:
    datetime-pattern: "yyyy-MM-dd HH:mm:ss"
    html:
      wrapper-class: "greeting"
      message-class: "message"
      metadata-class: "metadata"
    json:
      include-metadata: true
      pretty-print: true
```

## Интернационализация

Поддерживаемые языки:
- Русский (по умолчанию)
- English

Для смены языка используйте параметр `lang` при вызове:

```java
greeter.greet("John", new Locale("en"), GreetingFormat.TEXT);
```

## Пример использования REST API

```java
@RestController
public class GreeterController {
    
    private final Greeter greeter;
    
    @GetMapping("/greet/{name}")
    public String greet(
        @PathVariable String name,
        @RequestParam(required = false) String lang,
        @RequestParam(required = false) GreetingFormat format
    ) {
        Locale locale = lang != null ? new Locale(lang) : Locale.getDefault();
        return greeter.greet(name, locale, format != null ? format : GreetingFormat.TEXT);
    }
}
```

Примеры запросов:
- `GET /greet/John` - базовое приветствие
- `GET /greet/John?format=HTML` - HTML формат
- `GET /greet/John?lang=ru&format=JSON` - JSON формат на русском языке

(Примеры запросов находятся в : *Own_Library_And_Spring_Boot_Starter/greeter-example/src/main/http/requests.http*)

## Расширение функциональности

### Создание собственного форматтера

```java
public class MarkdownFormatter implements GreetingFormatter {
    @Override
    public String format(GreetingResponse response) {
        // Ваша логика форматирования
    }

    @Override
    public GreetingFormat getFormat() {
        return GreetingFormat.MARKDOWN;
    }
}
```


