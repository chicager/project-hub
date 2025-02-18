package org.example.listeners.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.listeners.event.OrderEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
Содержит три метода-слушателя:
  Общий слушатель для всех событий заказа
  Слушатель только для созданных заказов (условие CREATED)
  Слушатель только для завершенных заказов (условие COMPLETED)
*/
@Slf4j
@Component
public class OrderEventListener {

    /*
    Основные моменты использования слушателей:
    Аннотация @EventListener используется для пометки методов как слушателей событий
    Можно использовать условия в слушателях через condition
    События публикуются через ApplicationEventPublisher
    Слушатели могут обрабатывать события асинхронно (если добавить @Async)
    */


    //Это более общий лисенер, поэтому ставим ему очередь выполнения позже
    @Order(1)
    @EventListener
    public void handleOrderEvent(OrderEvent event) {
        log.info("★ Получено событие заказа: {}", event.getEventType());
        log.info("★ Заказ ID: {}, Статус: {}", event.getOrder().getId(), event.getOrder().getStatus());
    }

    /*
    T() - это специальная функция в SpEL (Spring Expression Language),
    которая используется для получения ссылки на класс. Это как Class.forName() в Java.
    В данном случае: T(com.example.listeners.enums.OrderEventTypeEnum) означает "получи мне класс OrderEventType"
    .CREATED - после получения класса мы обращаемся к его статической константе CREATED
    Полностью условие: #event.eventType == T(com.example.listeners.event.OrderEventType).CREATED
    можно прочитать как: "проверь, равен ли eventType у события (#event) константе CREATED из перечисления OrderEventType"
    T() нужен потому что в SpEL нельзя напрямую использовать имена классов, нужно явно указать, что мы хотим получить ссылку на класс.
    Это как разница между:
      OrderEventType.CREATED (прямое обращение в Java)
      T(OrderEventType).CREATED (обращение через SpEL)
    */
    @Order(0)
    @EventListener(condition = "#event.eventType == T(org.example.listeners.enums.OrderEventTypeEnum).CREATED")
    public void handleOrderCreatedEvent(OrderEvent event) {
        log.info("★ Новый заказ создан! ID: {}", event.getOrder().getId());
    }

    @Order(0)
    @EventListener(condition = "#event.eventType == T(org.example.listeners.enums.OrderEventTypeEnum).COMPLETED")
    public void handleOrderCompletedEvent(OrderEvent event) {
        log.info("★ Заказ выполнен! ID: {}, Сумма заказа: {}", event.getOrder().getId(), event.getOrder().getAmount());
    }
}
