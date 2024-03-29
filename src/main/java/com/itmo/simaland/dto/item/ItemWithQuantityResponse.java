package com.itmo.simaland.dto.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemWithQuantityResponse {
    private ItemResponse item;
    private Integer quantity;
}
