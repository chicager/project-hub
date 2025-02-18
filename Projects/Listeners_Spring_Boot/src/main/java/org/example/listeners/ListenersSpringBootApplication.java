package org.example.listeners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ListenersSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListenersSpringBootApplication.class, args);
	}

}

/*
Чтобы протестировать:
  Запустите приложение
  Отправьте POST-запрос на http://localhost:8080/api/orders/test
  В консоли вы увидите логи от различных слушателей

Основные моменты использования слушателей:
  Аннотация @EventListener используется для пометки методов как слушателей событий
  Можно использовать условия в слушателях через condition
  События публикуются через ApplicationEventPublisher
  Слушатели могут обрабатывать события асинхронно (если добавить @Async)

Это базовый пример, но он показывает основные принципы работы со слушателями в Spring Boot.
В реальных проектах слушатели часто используются для:
  Логирования
  Отправки уведомлений
  Обновления кэша
  Асинхронной обработки событий
  Интеграции с другими системами
*/


