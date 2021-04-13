package ru.kolumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.kolumarket.domain.Customer;
import ru.kolumarket.domain.Order;
import ru.kolumarket.domain.OrderItem;
import ru.kolumarket.domain.Product;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem,OrderItem.Id>, JpaSpecificationExecutor<OrderItem> {
    Optional<OrderItem> getOrderItemByProductAndOrder(Product product, Order order);
    List<OrderItem> getOrderItemsByOrder(Order order);
}
