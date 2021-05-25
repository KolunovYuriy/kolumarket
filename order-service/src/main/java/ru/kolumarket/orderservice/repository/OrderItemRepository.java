package ru.kolumarket.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.kolumarket.orderservice.domain.Order;
import ru.kolumarket.orderservice.domain.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem,OrderItem.Id>, JpaSpecificationExecutor<OrderItem> {
    Optional<OrderItem> getOrderItemByProductIdAndOrder(Long productId, Order order);
    List<OrderItem> getOrderItemsByOrder(Order order);
}
