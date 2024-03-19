package com.itmo.simaland.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;
}
