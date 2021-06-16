package ru.kolumarket.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolumarket.authservice.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}