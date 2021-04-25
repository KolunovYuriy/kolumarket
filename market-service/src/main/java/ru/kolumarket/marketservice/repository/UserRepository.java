package ru.kolumarket.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolumarket.marketservice.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

}