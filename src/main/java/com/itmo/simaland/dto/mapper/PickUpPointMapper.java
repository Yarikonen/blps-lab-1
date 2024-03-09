package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.pickUpPoint.PickUpPointRequest;
import com.itmo.simaland.dto.pickUpPoint.PickUpPointResponse;
import com.itmo.simaland.model.entity.PickUpPoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PickUpPointMapper {


    PickUpPointResponse toResponse(PickUpPoint pickUpPoint);

    @Mapping(source = "warehouseId", target = "warehouse.id")
    PickUpPoint toEntity(PickUpPointRequest request);
}
