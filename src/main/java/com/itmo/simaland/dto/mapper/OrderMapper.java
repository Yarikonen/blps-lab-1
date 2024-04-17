package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.order.CreateOrderRequest;
import com.itmo.simaland.dto.order.ItemQuantity;
import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.dto.paging.ListResponse;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Order;

import com.itmo.simaland.model.entity.OrderItem;
import com.itmo.simaland.service.ItemService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ItemService.class})
public interface OrderMapper {
    @Mapping(source = "customer", target = "customer.id")
    @Mapping(source = "address", target = "pickUpAddress")
    @Mapping(source="pickUpPointAddress", target="pickUpPointId")
    @Mapping(source="items", target="orderItems")
    Order mapToOrder(CreateOrderRequest request);

    @Mapping(source="itemId", target = "item")
    OrderItem mapToOrderItem(ItemQuantity itemQuantity);
    List<OrderItem> itemQuantityListToOrderItemList(List<ItemQuantity> list);

    @Mapping(source = "number", target = "pageNumber")
    ListResponse<OrderResponse> pageToOrderListResponse(Page<Order> page);

    OrderResponse mapToResponse(Order order);
}
