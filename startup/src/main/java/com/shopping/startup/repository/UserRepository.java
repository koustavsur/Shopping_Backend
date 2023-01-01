package com.shopping.startup.repository;

import com.shopping.startup.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    public Users findByEmail(String email);
    public void deleteUsersByEmail(String email);
}
