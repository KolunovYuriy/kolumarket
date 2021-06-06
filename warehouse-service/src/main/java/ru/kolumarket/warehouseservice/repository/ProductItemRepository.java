package ru.kolumarket.warehouseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kolumarket.warehouseservice.domain.ProductItem;
import ru.kolumarket.warehouseservice.domain.Warehouse;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem,ProductItem.Id> {
    Optional<ProductItem> getProductItemByProductIdAndWarehouse(Long productId, Warehouse warehouse);

    @Query("select sum(pi.count) from ProductItem pi where pi.productId = :productId")
    Integer getCountOfProductInAllWarehouses(Long productId);

}
