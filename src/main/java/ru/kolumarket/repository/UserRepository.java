package ru.kolumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolumarket.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

}