package com.itmo.simaland.model.entity;


import com.itmo.simaland.model.enums.AddressType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "_order")
@RequiredArgsConstructor
@Setter
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "pick_up_address")
    private String pickUpAddress;

    @Transient
    private AddressType addressType;

    @Transient
    private Long pickUpPointId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Column(name = "paid")
    private Boolean paid;

    public Double getTotal() {
        return orderItems.stream()
                .mapToDouble(item -> item.getItem().getPrice() * item.getQuantity())
                .sum();
    }
}
