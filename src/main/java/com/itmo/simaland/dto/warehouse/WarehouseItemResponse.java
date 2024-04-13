package com.itmo.simaland.dto.warehouse;

import com.itmo.simaland.dto.item.ItemWithQuantityResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WarehouseItemResponse {
    private Long warehouseId;
    private String warehouseAddress;
    private List<ItemWithQuantityResponse> items;
}
