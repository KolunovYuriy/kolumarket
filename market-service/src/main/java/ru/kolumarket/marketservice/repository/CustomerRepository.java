package ru.kolumarket.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.kolumarket.marketservice.domain.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {
    Optional<Customer> getCustomerBySessionId(String sessionId);
    Optional<Customer> getCustomerByUserId(int userId);
}
