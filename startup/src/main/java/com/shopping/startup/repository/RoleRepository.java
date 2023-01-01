package com.shopping.startup.repository;

import com.shopping.startup.entity.Roles;
import com.shopping.startup.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {

    public Roles findByRole(Role role);
}
