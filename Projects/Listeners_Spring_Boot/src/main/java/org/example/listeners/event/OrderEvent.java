package org.example.listeners.event;

import lombok.Getter;
import org.example.listeners.enums.OrderEventTypeEnum;
import org.example.listeners.model.Order;
import org.springframework.context.ApplicationEvent;

//Пользовательское событие, которое расширяет ApplicationEvent. Оно содержит информацию о заказе и тип события.

/*
Мы используем только @Getter, потому что:
 - Нам нужен только доступ на чтение полей (события обычно иммутабельны)
 - У нас уже есть свой конструктор, который должен вызывать super(source) для корректной работы ApplicationEvent

 Мы не можем использовать @RequiredArgsConstructor, потому что:
 - Нам обязательно нужно вызвать конструктор родительского класса ApplicationEvent с параметром source
 - Lombok не может автоматически сгенерировать такой конструктор с вызовом super()
 - Если бы это был обычный класс без наследования от ApplicationEvent, то мы могли бы использовать @RequiredArgsConstructor,
   Но в нашем случае это невозможно из-за необходимости специальной инициализации родительского класса.
*/
@Getter
public class OrderEvent extends ApplicationEvent {

    private final Order order;
    private final OrderEventTypeEnum eventType;

    public OrderEvent(Object source, Order order, OrderEventTypeEnum eventType) {
        super(source);
        this.order = order;
        this.eventType = eventType;
    }
}

/*
Объяснение ApplicationEvent:

Представьте школу. В школе есть звонок на урок/перемену. Когда звенит звонок:
  1. Ученики идут в класс
  2. Учителя идут преподавать
  3. Уборщица начинает мыть коридоры
  4. Охранник делает обход

Звонок (это наш ApplicationEvent) - просто сообщает "эй, что-то произошло!".
не знает, кто и что будет делать после этого сигнала. Он просто сообщает о событии.

В коде это выглядит так:

// 1. Создаём событие (звонок)
public class BellEvent extends ApplicationEvent {
    private final String type; // "LESSON_START" или "BREAK_TIME"

    public BellEvent(Object source, String type) {
        super(source);
        this.type = type;
    }
}

// 2. Где-то в коде звеним в звонок
bellEvent = new BellEvent(this, "LESSON_START");
applicationEventPublisher.publishEvent(bellEvent);

// 3. Разные части приложения реагируют на звонок
@EventListener
public void studentsReaction(BellEvent event) {
    if (event.getType().equals("LESSON_START")) {
        System.out.println("Ученики идут в класс");
    }
}

@EventListener
public void teachersReaction(BellEvent event) {
    if (event.getType().equals("LESSON_START")) {
        System.out.println("Учителя идут преподавать");
    }
}

@EventListener
public void cleanerReaction(BellEvent event) {
    if (event.getType().equals("LESSON_START")) {
        System.out.println("Уборщица моет коридоры");
    }
}

Главное преимущество: звонок просто звенит, он не должен знать обо всех, кто на него реагирует.
И если завтра добавится новый охранник - ему просто нужно "подписаться" на звонок, а сам звонок менять не надо.

То же самое с заказами:
Когда создаётся заказ - мы просто сообщаем "создан новый заказ" (звеним в звонок)
  А разные части системы сами решают, что им с этим делать:
  Одна часть отправляет email клиенту
  Другая часть обновляет склад
  Третья уведомляет менеджера

И всё это происходит автоматически, просто потому что прозвенел "звонок" (событие).
 */