package com.shopping.startup.entity;


import com.shopping.startup.model.LoginSource;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "userAddresses")
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email")
        }
)
public class Users extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "user_id", updatable = false)
    private Long userId;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_source", nullable = false, columnDefinition = "TEXT")
    private LoginSource loginSource;

    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(
            name = "role_id_fk",
            referencedColumnName = "role_id"
    )
    private Roles role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserAddress> userAddresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShoppingCart> shoppingCarts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("orderDate DESC")
    private List<ShopOrder> shopOrders;
}
