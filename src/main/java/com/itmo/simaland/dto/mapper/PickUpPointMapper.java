package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.pickUpPoint.PickUpPointRequest;
import com.itmo.simaland.dto.pickUpPoint.PickUpPointResponse;
import com.itmo.simaland.model.entity.PickUpPoint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PickUpPointMapper {
    PickUpPointResponse toResponse(PickUpPoint pickUpPoint);

    PickUpPoint toEntity(PickUpPointRequest request);
}
