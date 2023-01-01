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
@Table(name = "user_address")
public class UserAddress extends Auditable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", updatable = false)
    private Long addressId;

    @Column(name = "address_street_one", nullable = false, columnDefinition = "TEXT")
    private String addressStreetOne;

    @Column(name = "address_street_two", columnDefinition = "TEXT")
    private String addressStreetTwo;

    @Column(name = "city", nullable = false, columnDefinition = "TEXT")
    private String city;

    @Column(name = "state", nullable = false, columnDefinition = "TEXT")
    private String state;

    @Column(name = "pincode", nullable = false, columnDefinition = "TEXT")
    private String pincode;

    @Column(name = "country", nullable = false, columnDefinition = "TEXT")
    private String country;

    @Column(name = "phone", nullable = false, columnDefinition = "TEXT")
    private String phoneNumber;

    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name="user_id_fk",
            referencedColumnName = "user_id"
    )
    @JsonIgnore
    private Users user;


}
