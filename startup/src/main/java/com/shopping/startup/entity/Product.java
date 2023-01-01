package com.shopping.startup.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "product",
        uniqueConstraints = {
                @UniqueConstraint(name = "product_name_unique", columnNames = "product_name")
        }
)
public class Product extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false, columnDefinition = "TEXT")
    private String productName;

    @Column(name = "product_details", nullable = false, columnDefinition = "TEXT")
    private String productDetails;

    @Column(name = "product_image", nullable = false, columnDefinition = "TEXT")
    private String productImage;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_category_fk", referencedColumnName = "sub_category_id")
    @JsonIgnore
    private SubCategory subCategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    //@JsonIgnore
    private List<ProductVariation> productVariationList;
}
