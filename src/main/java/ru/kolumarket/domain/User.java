package ru.kolumarket.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String login;

    @Column
    private String password;

    @OneToOne(mappedBy = "user")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}