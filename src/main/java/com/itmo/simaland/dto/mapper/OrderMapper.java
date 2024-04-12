package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.order.CreateOrderRequest;
import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.dto.paging.ListResponse;
import com.itmo.simaland.model.entity.Order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer", target = "customer.id")
    @Mapping(source = "address", target = "pickUpAddress")
    @Mapping(source="pickUpPointAddress", target="pickUpPointId")
    @Mapping(source="items", target="orderItems")
    Order mapToOrder(CreateOrderRequest request);

    @Mapping(source = "number", target = "pageNumber")
    ListResponse<OrderResponse> pageToOrderListResponse(Page<Order> page);


    OrderResponse mapToResponse(Order order);
}
