package com.itmo.simaland.model.entity;


import com.itmo.simaland.model.enums.AddressType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "_order")
@AllArgsConstructor
@NoArgsConstructor
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


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "order_item", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;


    @Column(name = "paid")
    private Boolean paid;

}
