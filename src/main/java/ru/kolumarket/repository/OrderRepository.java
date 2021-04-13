package ru.kolumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolumarket.domain.Customer;
import ru.kolumarket.domain.Order;
import ru.kolumarket.domain.OrderItem;
import ru.kolumarket.domain.Product;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> getOrderByCustomerAndStatus(Customer customer, String status);
    Optional<Order> getOrderByCustomer(Customer customer);
}
