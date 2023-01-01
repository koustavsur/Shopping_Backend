package com.shopping.startup.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id", updatable = false)
    private Long shoppingCartId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_fk", referencedColumnName = "user_id")
    @JsonIgnore
    private Users user;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_variation_fk", referencedColumnName = "product_variation_id")
    private ProductVariation productVariation;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
