package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.order.CreateOrderRequest;
import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    @Mapping(source = "customer", target = "customer.id")
    Order mapToOrder(CreateOrderRequest request);

    Item mapToItem(Long id);


    @Mapping(source="id", target="id")
    OrderResponse mapToResponse(Order order);

}
