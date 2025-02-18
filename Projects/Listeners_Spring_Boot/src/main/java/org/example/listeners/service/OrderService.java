package org.example.listeners.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.listeners.enums.OrderEventTypeEnum;
import org.example.listeners.enums.OrderStatusEnum;
import org.example.listeners.event.OrderEvent;
import org.example.listeners.model.Order;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/*
Сервис, который публикует события
Использует ApplicationEventPublisher для публикации событий
Создает и публикует события при создании и завершении заказа
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    //События публикуются через ApplicationEventPublisher
    private final ApplicationEventPublisher eventPublisher;

    public void createOrder(Order order) {
        // Логика создания заказа
        log.info("▶▶▶ Создание заказа с ID: {}", order.getId());
        order.setStatus(OrderStatusEnum.CREATED);
        eventPublisher.publishEvent(new OrderEvent(this, order, OrderEventTypeEnum.CREATED));
    }

    public void completeOrder(Order order) {
        // Логика завершения заказа
        log.info("▶▶▶ Завершение заказа с ID: {}", order.getId());
        order.setStatus(OrderStatusEnum.COMPLETED);
        eventPublisher.publishEvent(new OrderEvent(this, order, OrderEventTypeEnum.COMPLETED));
    }
}
