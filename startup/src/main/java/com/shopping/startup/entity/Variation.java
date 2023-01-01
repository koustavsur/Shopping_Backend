package com.shopping.startup.entity;


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
@Table(
        name = "variation",
        uniqueConstraints = {
                @UniqueConstraint(name = "variation_unique", columnNames = "variation_name")
        }
)
public class Variation extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variation_id", updatable = false)
    private Long variationId;

    @Column(name = "variation_name", columnDefinition = "TEXT", nullable = false)
    private String variationName;

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VariationValue> variationValues;
}
