package ru.kolumarket.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolumarket.marketservice.domain.Customer;
import ru.kolumarket.marketservice.domain.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> getOrderByCustomerAndStatus(Customer customer, String status);
    Optional<Order> getOrderByCustomer(Customer customer);
}
