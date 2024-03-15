package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.dto.paging.ListResponse;
import com.itmo.simaland.dto.warehouse.WarehouseRequest;
import com.itmo.simaland.dto.warehouse.WarehouseResponse;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.model.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    Warehouse toEntity(WarehouseRequest request);

    WarehouseResponse toResponse(Warehouse warehouse);

    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "number", target = "pageNumber")
    @Mapping(source="id", target="id")
    ListResponse<WarehouseResponse> pageToItemListResponse(Page<Warehouse> page);
}
