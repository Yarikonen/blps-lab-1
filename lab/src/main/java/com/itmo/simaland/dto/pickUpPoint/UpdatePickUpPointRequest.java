package com.itmo.simaland.dto.pickUpPoint;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePickUpPointRequest {
    private String address;
    private Long warehouseId;
}
