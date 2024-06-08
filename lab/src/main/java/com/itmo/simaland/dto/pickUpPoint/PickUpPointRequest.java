package com.itmo.simaland.dto.pickUpPoint;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PickUpPointRequest {
    @NotNull(message = "Address can not be empty")
    private String address;

    @NotNull(message = "Warehouse id should not be empty")
    private Long warehouseId;
}
