package ru.kolumarket.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolumarket.authservice.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

}