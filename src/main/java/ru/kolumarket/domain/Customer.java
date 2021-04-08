package ru.kolumarket.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "session_id")
    private String sessionId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "customer")
    //@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<OrderItem> orderItems;

    @Override
    public String toString() {
        return String.format("Customer: [%d %s]", id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Customer(String name) {
        this.name = name;
    }

    public Customer(String name, String sessionId) {
        this.name = name;
        this.sessionId = sessionId;
        this.user = new User();
        this.orderItems = new ArrayList<>();
    }
    public Customer(String name, User user) {
        if (name.equals(""))
            this.name = user.getLogin();
        else this.name = name;
        this.user = user;
        this.orderItems = new ArrayList<>();
    }
}
