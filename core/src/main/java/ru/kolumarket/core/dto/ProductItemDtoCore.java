package ru.kolumarket.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductItemDtoCore {
    private Long productId;
    private Long warehouseId;
    private String warehouse;
    private String productName;
    private int warehouseProductCount;
    private int count;

    @Override
    public String toString() {
        return "ProductItemDtoCore{" +
                "productId=" + productId +
                ", warehouseId=" + warehouseId +
                ", warehouse='" + warehouse + '\'' +
                ", productName='" + productName + '\'' +
                ", warehouseProductCount=" + warehouseProductCount +
                ", count=" + count +
                '}';
    }
}
