package ru.kolumarket.orderservice.payment;

import ru.kolumarket.orderservice.domain.Order;

public interface PaymentStrategy {
    boolean pay(Order order);
}
