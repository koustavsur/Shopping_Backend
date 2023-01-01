package com.shopping.startup.service;


import com.shopping.startup.entity.ProductVariation;
import com.shopping.startup.entity.ShoppingCart;
import com.shopping.startup.entity.Users;
import com.shopping.startup.model.ShoppingCartRequest;
import com.shopping.startup.repository.ProductVariationRepository;
import com.shopping.startup.repository.ShoppingCartRepository;
import com.shopping.startup.repository.UserRepository;
import com.shopping.startup.util.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductVariationRepository productVariationRepository;
    private final UserRepository userRepository;
    private final Validation validation;


    public List<ShoppingCart> getCurrentUserCart() {

        String userEmail = validation.getCurrentUserEmail();
        if(userEmail == null)
            return null;

        return getUserCart(userEmail);
    }

    public List<ShoppingCart> getUserCart(String email){
        if(!validation.isFieldValid(email))
            return null;

        Users user = userRepository.findByEmail(email.strip());
        if(user == null)
            return null;

        return shoppingCartRepository.findAllByUser(user);
    }

    @Transactional
    public List<ShoppingCart> addOrUpdateUserCart(List<ShoppingCartRequest> shoppingCartRequests) {

        if(shoppingCartRequests == null || shoppingCartRequests.size() == 0)
            return null;

        List<ShoppingCart> shoppingCartList = new ArrayList<>();

        for(ShoppingCartRequest cart: shoppingCartRequests){
            if(validation.isFieldValid(cart.getUserEmail()) && validation.isFieldValid(cart.getQuantity())
                    && validation.isFieldValid(cart.getProductVariationId())){
                Optional<ProductVariation> productVariation = productVariationRepository.findById(Long.valueOf(cart.getProductVariationId().strip()));
                if(productVariation.isEmpty())
                    continue;

                Users user = userRepository.findByEmail(cart.getUserEmail().strip());
                if(user == null)
                    continue;

                ShoppingCart shoppingCart = ShoppingCart.builder()
                        .productVariation(productVariation.get())
                        .user(user)
                        .quantity(Integer.valueOf(cart.getQuantity().strip()))
                        .build();

                if(validation.isFieldValid(cart.getId()))
                    shoppingCart.setShoppingCartId(Long.valueOf(cart.getId().strip()));

                shoppingCartList.add(shoppingCart);
            }
        }

        return shoppingCartRepository.saveAll(shoppingCartList);
    }

    @Transactional
    public ShoppingCart deleteUserCart(List<ShoppingCartRequest> shoppingCartRequests) {

        if(shoppingCartRequests == null || shoppingCartRequests.size() == 0)
            return null;

        List<Long> ids = new ArrayList<>();

        for(ShoppingCartRequest cart: shoppingCartRequests){
            if(validation.isFieldValid(cart.getId()))
                ids.add(Long.valueOf(cart.getId().strip()));

        }
        shoppingCartRepository.deleteAllById(ids);
        return ShoppingCart.builder().build();
    }
}
