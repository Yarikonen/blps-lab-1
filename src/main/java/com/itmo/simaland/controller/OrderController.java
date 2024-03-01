package com.itmo.simaland.controller;


import com.itmo.simaland.dto.mapper.OrderMapper;
import com.itmo.simaland.dto.order.CreateOrderRequest;
import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.dto.paging.PaginationRequest;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;


@RestController
@Tag(name = "Order Controller", description = "Order Controller")
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/list")
    @Operation(summary = "Get all orders")
    @ApiResponse(responseCode = "200", description = "OK")
    Page<Order> getOrders(@Parameter(description = "Page number, Page size") PaginationRequest pageRequest) {
        return orderService.getOrders(pageRequest.toPageRequest());
    }

    @PostMapping("/create")
    @Operation(summary = "Create order")
    @ApiResponse(responseCode = "201", description = "OK")
    public OrderResponse createOrder(CreateOrderRequest request) {
        return Stream.of(request)
                .map(orderMapper::mapToOrder)
                .map(orderService::createOrder)
                .map(orderMapper::mapToResponse)
                .findFirst().get();
    }









}
