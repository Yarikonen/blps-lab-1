package com.itmo.simaland.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WarehouseRequest {
    @NotNull(message = "Address can not be null")
    private String address;
}

