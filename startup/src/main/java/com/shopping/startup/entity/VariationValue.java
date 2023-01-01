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
@Table(
        name = "variation_value",
        uniqueConstraints = {
                @UniqueConstraint(name = "variation_value_unique",
                        columnNames = "variation_value_name")
        }
)
public class VariationValue extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variation_value_id", updatable = false)
    private Long variationValueId;

    @Column(name = "variation_value_name", columnDefinition = "TEXT", nullable = false)
    private String variationValueName;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name="variation_fk",
            referencedColumnName = "variation_id"
    )
    @JsonIgnore
    private Variation variation;
}
