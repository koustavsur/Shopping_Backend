package com.shopping.startup.service;


import com.shopping.startup.entity.ShopOrder;
import com.shopping.startup.entity.UserAddress;
import com.shopping.startup.entity.Users;
import com.shopping.startup.model.OrderStatus;
import com.shopping.startup.model.PaymentMethod;
import com.shopping.startup.model.ShopOrderRequest;
import com.shopping.startup.repository.ShopOrderRepository;
import com.shopping.startup.repository.UserAddressRepository;
import com.shopping.startup.repository.UserRepository;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopOrderService {

    private final ShopOrderRepository shopOrderRepository;
    private final UserAddressRepository addressRepository;
    private final UserRepository userRepository;
    private final Validation validation;


    public List<ShopOrder> getAllOrders() {

        return shopOrderRepository.findAll(Sort.by(Sort.Direction.DESC,"orderDate"));
    }

    public List<ShopOrder> getAllOrderByStatus(String orderStatus) {
        if(!validation.isFieldValid(orderStatus))
            return null;
        OrderStatus status;
        try {
            status = OrderStatus.valueOf(orderStatus);
        } catch (Exception e){
            log.info("Exception while converting order status", e);
            return null;
        }

        return shopOrderRepository.findAllByOrderStatus(Sort.by(Sort.Direction.DESC,"orderDate"), status);
    }

    public List<ShopOrder> getAllOrderByUser(String userEmail) {
        if(!validation.isFieldValid(userEmail))
            return null;

        Users user = userRepository.findByEmail(userEmail);
        if(user == null)
            return null;

        return shopOrderRepository.findAllByUser(Sort.by(Sort.Direction.DESC,"orderDate"), user);
    }


    public List<ShopOrder> getAllCurrentUserOrders() {
        String email = validation.getCurrentUserEmail();
        if(email == null)
            return null;

        return getAllOrderByUser(email);
    }

    public ShopOrder addOrder(ShopOrderRequest orderRequest) {

        if(!isRequestValid(orderRequest))
            return null;

        OrderStatus status;
        PaymentMethod paymentMethod = PaymentMethod.MANUAL;

        try{
            status = OrderStatus.valueOf(orderRequest.getOrderStatus().strip());

            if(validation.isFieldValid(orderRequest.getPaymentMethod()))
                paymentMethod = PaymentMethod.valueOf(orderRequest.getPaymentMethod().strip());

        } catch (Exception e) {
            log.info("Exception while converting order status / payment method", e);
            return null;
        }

        Users user = userRepository.findByEmail(orderRequest.getUserEmail().strip());
        if(user == null)
            return null;

        Optional<UserAddress> address = addressRepository.findById(Long.valueOf(orderRequest.getAddressId().strip()));
        if(address.isEmpty())
            return null;

        Date orderDate = new Date();

        ShopOrder order = ShopOrder.builder()
                .orderStatus(status)
                .paymentMethod(paymentMethod)
                .user(user)
                .orderTotal(Float.valueOf(orderRequest.getOrderTotal()))
                .userAddress(address.get())
                .orderDate(orderDate)
                .build();

        return shopOrderRepository.save(order);

    }



    private boolean isRequestValid(ShopOrderRequest orderRequest) {
        if(!(validation.isFieldValid(orderRequest.getAddressId()) && validation.isFieldValid(orderRequest.getOrderStatus())
                && validation.isFieldValid(orderRequest.getOrderTotal()))){
            return false;
        }

        if(validation.isFieldValid(orderRequest.getUserEmail())) {
            if (!validation.isCurrentUser(orderRequest.getUserEmail().strip()))
                return validation.isAdmin();

        } else {
            orderRequest.setUserEmail(validation.getCurrentUserEmail());
        }

        return true;
    }

    @Transactional
    public ShopOrder updateOrder(ShopOrderRequest orderRequest) {

        if(!(isRequestValid(orderRequest) && validation.isFieldValid(orderRequest.getId())))
            return null;

        Optional<ShopOrder> order = shopOrderRepository.findById(Long.valueOf(orderRequest.getId().strip()));
        if(order.isEmpty())
            return null;

        ShopOrder updatedOrder = order.get();

        try{
            updatedOrder.setOrderStatus(OrderStatus.valueOf(orderRequest.getOrderStatus().strip()));

            if(validation.isFieldValid(orderRequest.getPaymentMethod()))
                updatedOrder.setPaymentMethod(PaymentMethod.valueOf(orderRequest.getPaymentMethod().strip()));

        } catch (Exception e) {
            log.info("Exception while converting order status / payment method", e);
            return null;
        }

        Optional<UserAddress> address = addressRepository.findById(Long.valueOf(orderRequest.getAddressId().strip()));
        if(address.isEmpty())
            return null;

        updatedOrder.setOrderTotal(Float.valueOf(orderRequest.getOrderTotal().strip()));
        updatedOrder.setUserAddress(address.get());

        return shopOrderRepository.save(updatedOrder);

    }

    @Transactional
    public ShopOrder deleteOrders(List<ShopOrderRequest> orderRequest) {

        if(orderRequest == null || orderRequest.isEmpty())
            return null;

        List<Long> ids = new ArrayList<>();

        for(ShopOrderRequest order: orderRequest){
            if(validation.isFieldValid(order.getId()))
                ids.add(Long.valueOf(order.getId().strip()));
        }

        shopOrderRepository.deleteAllById(ids);
        return ShopOrder.builder().build();
    }
}
