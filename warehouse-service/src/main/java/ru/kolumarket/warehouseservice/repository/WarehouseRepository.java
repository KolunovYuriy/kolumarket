package ru.kolumarket.warehouseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolumarket.warehouseservice.domain.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
}
