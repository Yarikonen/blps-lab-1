package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.item.ItemResponse;
import com.itmo.simaland.dto.order.CreateOrderRequest;
import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.dto.paging.ListResponse;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    @Mapping(source = "customer", target = "customer.id")
    @Mapping(source = "address", target = "pickUpAddress")
    @Mapping(source="pickUpPointAddress", target="pickUpPointId")
    Order mapToOrder(CreateOrderRequest request);

    Item mapToItem(Long id);

    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "number", target = "pageNumber")
    ListResponse<OrderResponse> pageToOrderListResponse(Page<Order> page);


    @Mapping(source="id", target="id")
    OrderResponse mapToResponse(Order order);

}
