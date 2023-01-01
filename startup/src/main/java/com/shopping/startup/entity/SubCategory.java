package com.shopping.startup.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sub_category")
public class SubCategory extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id", updatable = false)
    private Long subCategoryId;

    @Column(name = "sub_category_name", nullable = false, columnDefinition = "TEXT")
    private String subCategoryName;

    @Column(name = "sub_category_image", nullable = false, columnDefinition = "TEXT")
    private String subCategoryImage;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name="category_fk",
            referencedColumnName = "category_id"
    )
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Product> products;
}
