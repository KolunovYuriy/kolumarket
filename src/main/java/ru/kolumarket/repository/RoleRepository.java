package ru.kolumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolumarket.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}