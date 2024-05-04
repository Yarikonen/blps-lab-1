package com.itmo.simaland.dto.warehouse;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseItemRequest {
    private Long itemId;
    @Positive(message = "Quantity should be more than 0")
    private Integer quantity;
}

