package com.shopping.startup.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_variation")
public class ProductVariation extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_variation_id", updatable = false)
    private Long productVariationId;

    @Column(name = "product_variation_qty", nullable = false)
    private Integer productQty;

    @Column(name = "product_variation_price", nullable = false)
    private Float productPrice;

    @Column(name = "product_variation_image")
    private String productVariationImage;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_fk", referencedColumnName = "product_id")
    @JsonIgnore
    private Product product;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "variation_value_fk", referencedColumnName = "variation_value_id")
    private VariationValue variationValue;


}
