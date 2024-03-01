package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.order.CreateOrderRequest;
import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order mapToOrder(CreateOrderRequest request);

    Item mapToItem(Long id);


    OrderResponse mapToResponse(Order order);

}
