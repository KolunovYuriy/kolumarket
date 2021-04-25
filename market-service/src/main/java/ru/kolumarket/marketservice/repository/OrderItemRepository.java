package ru.kolumarket.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.kolumarket.marketservice.domain.Order;
import ru.kolumarket.marketservice.domain.OrderItem;
import ru.kolumarket.marketservice.domain.Product;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem,OrderItem.Id>, JpaSpecificationExecutor<OrderItem> {
    Optional<OrderItem> getOrderItemByProductAndOrder(Product product, Order order);
    List<OrderItem> getOrderItemsByOrder(Order order);
}
