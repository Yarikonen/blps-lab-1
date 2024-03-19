package com.itmo.simaland.controller;


import com.itmo.simaland.dto.mapper.OrderMapper;
import com.itmo.simaland.dto.order.CreateOrderRequest;
import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.dto.paging.ListResponse;
import com.itmo.simaland.dto.paging.PaginationRequest;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "Order Controller", description = "Order Controller")
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    @Operation(summary = "Get all orders")
    @ApiResponse(responseCode = "200", description = "Order list", content = @Content)
    ListResponse<OrderResponse> getOrders(PaginationRequest paginationRequest) {

        PageRequest pageRequest = paginationRequest.toPageRequest();
        Page<Order> page = orderService.getOrders(pageRequest);

        return orderMapper.pageToOrderListResponse(page);
    }

    @PostMapping
    @Operation(summary = "Create order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Incorrect data format", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found ", content = @Content)
    }
    )
    public OrderResponse createOrder(@RequestBody @Valid CreateOrderRequest request) {
        Order order = orderMapper.mapToOrder(request);
        return orderMapper.mapToResponse(orderService.createOrder(order));
    }

    @DeleteMapping("/{id}")
    @Operation(summary= "Delete order")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Order deleted"),
                    @ApiResponse(responseCode = "204", description = "Order not found")
            }
    )
    public void deleteOrder(@PathVariable Long id) {
        orderService.removeOrderById(id);
    }
}
