package org.example.listeners.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.listeners.enums.OrderStatusEnum;
import org.example.listeners.model.Order;
import org.example.listeners.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Простой контроллер для тестирования
@Slf4j
@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/test")
    public String testOrderEvents() {
        log.info("=================== ТЕСТ СОБЫТИЙ ЗАКАЗА ===================");
        var order = new Order(1L, OrderStatusEnum.NEW, 100.0);
        orderService.createOrder(order);
        orderService.completeOrder(order);
        log.info("=================== ТЕСТ ЗАВЕРШЕН ===================\n");
        return "События заказа протестированы";
    }
}
