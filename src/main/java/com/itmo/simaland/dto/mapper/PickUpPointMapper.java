package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.dto.paging.ListResponse;
import com.itmo.simaland.dto.pickUpPoint.PickUpPointRequest;
import com.itmo.simaland.dto.pickUpPoint.PickUpPointResponse;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.model.entity.PickUpPoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PickUpPointMapper {


    PickUpPointResponse toResponse(PickUpPoint pickUpPoint);

    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "number", target = "pageNumber")
    ListResponse<PickUpPointResponse> pageToPickUpPointListResponse(Page<PickUpPoint> page);

    @Mapping(source = "warehouseId", target = "warehouse.id")
    PickUpPoint toEntity(PickUpPointRequest request);
}
