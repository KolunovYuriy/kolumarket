package ru.kolumarket.orderservice.payment;

import ru.kolumarket.orderservice.domain.Order;

public class PayByWebMoney implements PaymentStrategy {
    @Override
    public boolean pay(Order order) {
        System.out.println("Paying using Web Money");
        return true;
    }
}
