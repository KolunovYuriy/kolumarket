package ru.kolumarket.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "product_items")
public class ProductItem {

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Id implements Serializable {
        @Column(name = "warewarehouse_id")
        private Long warehouseId;

        @Column(name = "product_id")
        private Long productId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductItem.Id id = (ProductItem.Id) o;
            return Objects.equals(warehouseId, id.warehouseId) && Objects.equals(productId, id.productId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(warehouseId, productId);
        }
    }

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne
    @JoinColumn(
            name = "warehouse_id",
            insertable = false, updatable = false
    )
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            insertable = false, updatable = false
    )
    private Product product;

    @Column(name = "count")
    private int count;

    public ProductItem(Product product, Warehouse warehouse) {
        this.product = product;
        this.warehouse = warehouse;
        this.id.productId = product.getId();
        this.id.warehouseId = warehouse.getId();
        this.count = 0;
        warehouse.getProductItems().add(this);
        product.getProductItems().add(this);
    }

}
