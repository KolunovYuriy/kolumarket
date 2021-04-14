package ru.kolumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolumarket.domain.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
}
