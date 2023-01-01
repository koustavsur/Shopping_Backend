package com.shopping.startup.repository;

import com.shopping.startup.entity.ShoppingCart;
import com.shopping.startup.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    public List<ShoppingCart> findAllByUser(Users user);
}
