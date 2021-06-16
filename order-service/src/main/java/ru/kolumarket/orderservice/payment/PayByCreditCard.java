package ru.kolumarket.orderservice.payment;

import ru.kolumarket.orderservice.domain.Order;

public class PayByCreditCard implements PaymentStrategy {
    @Override
    public boolean pay(Order order) {
        System.out.println("Paying using Credit Card");
        return true;
    }
}
