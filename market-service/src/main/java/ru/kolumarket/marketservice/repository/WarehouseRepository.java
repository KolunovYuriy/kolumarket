package ru.kolumarket.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolumarket.marketservice.domain.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
}
