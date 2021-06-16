package ru.kolumarket.product.component;

import org.springframework.stereotype.Component;

@Component
public class WarehouseDomainEventMessageReceiver {

    public void receiveMessage(byte[] message) {
        System.out.println("Received from topic <" + new String(message) + ">");
    }
}