package com.itmo.simaland.dto.mapper;

import com.itmo.simaland.dto.order.CreateOrderRequest;
import com.itmo.simaland.dto.order.ItemQuantity;
import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.dto.paging.ListResponse;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.model.entity.OrderItem;
import com.itmo.simaland.service.ItemService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Autowired
    private ItemService itemService;

    @Mapping(source = "customer", target = "customer.id")
    @Mapping(source = "address", target = "pickUpAddress")
    @Mapping(source="pickUpPointAddress", target="pickUpPointId")
    @Mapping(source="items", target="orderItems")
    public abstract Order mapToOrder(CreateOrderRequest request);

    @AfterMapping
    public void fillOrderItems(@MappingTarget Order order, CreateOrderRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (ItemQuantity itemQuantity : request.getItems()) {
            Item item = itemService.getItemById(itemQuantity.getItemId());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setQuantity(itemQuantity.getQuantity());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
    }

    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "number", target = "pageNumber")
    public abstract ListResponse<OrderResponse> pageToOrderListResponse(Page<Order> page);


    @Mapping(source="id", target="id")
    public abstract OrderResponse mapToResponse(Order order);
}
