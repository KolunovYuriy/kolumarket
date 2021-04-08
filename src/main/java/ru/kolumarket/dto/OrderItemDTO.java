package ru.kolumarket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kolumarket.domain.OrderItem;


@Data
@NoArgsConstructor
public class OrderItemDTO {
    private String title;
    private long customerId;
    private int count;

    public OrderItemDTO(OrderItem orderItem) {
        this.title = orderItem.getProduct().getTitle();
        this.customerId = orderItem.getCustomer().getId();
        this.count = orderItem.getCount();
    }

}
