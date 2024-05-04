package com.itmo.simaland.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "daily_sales_report")
@Getter
@Setter
public class DailySalesReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @Column(name = "total_items_sold")
    private int totalItemsSold;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;
}

