package ru.kolumarket.orderservice.domain;

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

    @Column(
            name = "product_id",
            insertable = false, updatable = false
    )
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "count")
    private int count;

    public OrderItem(Long productId, Order order) {
        fillProductAndOrderParams(productId, order);
    }

    public OrderItem(Long productId, String productName, Order order) {
        fillProductAndOrderParams(productId, order);

    }

    private void fillProductAndOrderParams(Long productId, Order order) {
        this.productId = productId;
        this.order = order;
        this.id.productId = productId;
        this.id.orderId = order.getId();
        this.count = 0;
        order.getOrderItems().add(this);
    }

}
