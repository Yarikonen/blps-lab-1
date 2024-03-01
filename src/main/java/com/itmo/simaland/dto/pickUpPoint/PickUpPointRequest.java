package com.itmo.simaland.dto.pickUpPoint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PickUpPointRequest {
    private String address;
    private Long warehouseId;
}
