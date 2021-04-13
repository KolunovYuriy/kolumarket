package ru.kolumarket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kolumarket.domain.OrderItem;


@Data
@NoArgsConstructor
public class OrderItemDTO {
    private String title;
    private int count;

    public OrderItemDTO(OrderItem orderItem) {
        this.title = orderItem.getProduct().getTitle();
        this.count = orderItem.getCount();
    }

}
