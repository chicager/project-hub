package org.example.listeners.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.listeners.enums.OrderStatusEnum;

//Простая модель заказа с основными полями.

@Data
@AllArgsConstructor
public class Order {

    private Long id;
    private OrderStatusEnum status;
    private Double amount;
}
