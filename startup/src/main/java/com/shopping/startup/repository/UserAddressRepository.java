package com.shopping.startup.repository;

import com.shopping.startup.entity.UserAddress;
import com.shopping.startup.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    public List<UserAddress> findByUser(Users user);
}
