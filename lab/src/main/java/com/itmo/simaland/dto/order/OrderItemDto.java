package com.itmo.simaland.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    String name;
    Integer quantity;
    BigDecimal totalPrice;
}
