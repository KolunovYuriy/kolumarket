package ru.kolumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolumarket.domain.ProductItem;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem,ProductItem.Id> {
}
