package com.itmo.simaland.dto.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemQuantity {
    private Long itemId;
    private Integer quantity;
}

