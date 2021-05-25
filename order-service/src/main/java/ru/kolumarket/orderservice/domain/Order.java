package ru.kolumarket.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            insertable = false, updatable = false
    )
    private Customer customer;

    public Order(Customer customer, OrderStatus status) {
        this.customer = customer;
        this.status = status.name();
        this.orderItems = new ArrayList<>();
    };
}
