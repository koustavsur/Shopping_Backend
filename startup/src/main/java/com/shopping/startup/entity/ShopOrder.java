package com.shopping.startup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopping.startup.model.OrderStatus;
import com.shopping.startup.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shop_order")
public class ShopOrder extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_order_id", updatable = false)
    private Long shopOrderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, columnDefinition = "TEXT")
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, columnDefinition = "TEXT")
    private PaymentMethod paymentMethod;

    @Column(name = "order_total", nullable = false)
    private Float orderTotal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_fk", referencedColumnName = "user_id")
    @JsonIgnore
    private Users user;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_fk", referencedColumnName = "address_id")
    private UserAddress userAddress;

}
