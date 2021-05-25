package ru.kolumarket.orderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kolumarket.orderservice.domain.OrderItem;

@Data
@NoArgsConstructor
public class OrderItemDTO {
    private String title;
    private int count;
    private Long productId;

    public OrderItemDTO(OrderItem orderItem) {
        //this.title = orderItem.getProduct().getTitle();
        this.productId = orderItem.getProductId();
        this.count = orderItem.getCount();
    }

}
