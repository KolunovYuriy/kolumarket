package ru.kolumarket.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolumarket.marketservice.domain.ProductItem;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem,ProductItem.Id> {
}
