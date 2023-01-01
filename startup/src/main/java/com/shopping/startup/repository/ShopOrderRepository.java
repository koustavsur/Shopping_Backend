package com.shopping.startup.repository;

import com.shopping.startup.entity.ShopOrder;
import com.shopping.startup.entity.Users;
import com.shopping.startup.model.OrderStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Long> {

    public List<ShopOrder> findAllByOrderStatus(Sort orderDate, OrderStatus orderStatus);
    public List<ShopOrder> findAllByUser(Sort orderDate, Users user);
}
