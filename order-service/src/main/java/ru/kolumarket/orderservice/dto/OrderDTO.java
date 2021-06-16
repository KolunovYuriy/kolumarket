package ru.kolumarket.orderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kolumarket.orderservice.domain.Order;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderDTO {
    private long id;
    private String status;
    private String customer;
    private List<OrderItemDTO> orderItems;

    public OrderDTO (Order order) {
        this.id = order.getId();
        this.status = order.getStatus();
        this.customer = order.getCustomer().getName();
        this.orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDTO(orderItem)).collect(Collectors.toList());
    }

}
