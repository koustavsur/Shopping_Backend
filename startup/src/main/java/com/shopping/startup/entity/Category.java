package com.shopping.startup.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "subCategories")
@Entity
@Table(
        name = "category",
        uniqueConstraints = {
                @UniqueConstraint(name = "category_name_unique", columnNames = "category_name")
        }
)
public class Category extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", updatable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable = false, columnDefinition = "TEXT")
    private String categoryName;

    @Column(name = "category_image", columnDefinition = "TEXT")
    private String categoryImage;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<SubCategory> subCategories;
}
