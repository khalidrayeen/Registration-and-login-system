package com.example.registration.repository;

import com.example.registration.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Rolerepository extends JpaRepository<Role,Long> {

    Role findByName(String name);
}
