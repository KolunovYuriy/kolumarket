package ru.kolumarket.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kolumarket.core.domain.UserInfo;

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

    @Column(name = "user_id")
    private int userId;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

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

    public Customer(String name, UserInfo user) {
        if (name.equals(""))
            this.name = "Customer_"+user.getUserId();
        else this.name = name;
        this.userId = user.getUserId().intValue();
        this.orders = new ArrayList<>();
    }
}
