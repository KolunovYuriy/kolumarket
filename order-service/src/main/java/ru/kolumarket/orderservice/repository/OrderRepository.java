package ru.kolumarket.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolumarket.orderservice.domain.Customer;
import ru.kolumarket.orderservice.domain.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> getOrderByCustomerAndStatus(Customer customer, String status);
    Optional<Order> getOrderByCustomer(Customer customer);
}
