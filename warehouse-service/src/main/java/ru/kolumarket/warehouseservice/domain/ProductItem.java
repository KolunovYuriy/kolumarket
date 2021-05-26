package ru.kolumarket.warehouseservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

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
        @Column(name = "warehouse_id")
        private Long warehouseId;

        @Column(name = "product_id")
        private Long productId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
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
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Warehouse warehouse;

    @Column(
            name = "product_id",
            insertable = false, updatable = false
    )
    private Long productId;

    @Column(name = "count")
    private int count;

    public ProductItem(Long productId, Warehouse warehouse) {
        this.productId = productId;
        this.warehouse = warehouse;
        this.id.productId = productId;
        this.id.warehouseId = warehouse.getId();
        this.count = 0;
        warehouse.getProductItems().add(this);
    }

}
