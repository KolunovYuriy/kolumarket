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
@Table(name = "order_items")
public class OrderItem {

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Id implements Serializable {
        @Column(name = "customer_id")
        private Long customerId;

        @Column(name = "product_id")
        private Long productId;


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return Objects.equals(customerId, id.customerId) && Objects.equals(productId, id.productId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(customerId, productId);
        }
    }

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            insertable = false, updatable = false
    )
    private Customer customer;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            insertable = false, updatable = false
    )
    private Product product;

    @Column(name = "count")
    private int count;

    public OrderItem(Product product, Customer customer) {
        this.product = product;
        this.customer = customer;
        this.id.productId = product.getId();
        this.id.customerId = customer.getId();
        this.count = 0;
        customer.getOrderItems().add(this);
        product.getOrderItems().add(this);
    }

}
