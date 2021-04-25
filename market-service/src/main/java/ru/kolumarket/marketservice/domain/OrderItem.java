package ru.kolumarket.marketservice.domain;

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
@Table(name = "order_items")
public class OrderItem {

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Id implements Serializable {
        @Column(name = "order_id")
        private Long orderId;

        @Column(name = "product_id")
        private Long productId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return Objects.equals(orderId, id.orderId) && Objects.equals(productId, id.productId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, productId);
        }
    }

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne
    @JoinColumn(
            name = "order_id",
            insertable = false, updatable = false
    )
    private Order order;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            insertable = false, updatable = false
    )
    private Product product;

    @Column(name = "count")
    private int count;

    public OrderItem(Product product, Order order) {
        this.product = product;
        this.order = order;
        this.id.productId = product.getId();
        this.id.orderId = order.getId();
        this.count = 0;
        order.getOrderItems().add(this);
        product.getOrderItems().add(this);
    }

}
