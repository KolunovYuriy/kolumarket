package ru.kolumarket.warehouseservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kolumarket.warehouseservice.domain.ProductItem;

@Data
@NoArgsConstructor
public class ProductItemDTO {
    private Long productId;
    private Long warehouseId;
    private String warehouse;
    private String productName;
    private int count;

    public ProductItemDTO(ProductItem p, String productName) {
        this.productId = p.getProductId();
        this.warehouseId = p.getWarehouse().getId();
        this.warehouse = p.getWarehouse().getLocation();
        this.productName = productName;
        this.count = p.getCount();
    }
}
