package com.itmo.simaland.controller;


import com.itmo.simaland.dto.mapper.OrderMapper;
import com.itmo.simaland.dto.order.CreateOrderRequest;
import com.itmo.simaland.dto.order.OrderResponse;
import com.itmo.simaland.dto.paging.ListResponse;
import com.itmo.simaland.dto.paging.PaginationRequest;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.service.ItemService;
import com.itmo.simaland.service.OrderService;
import com.itmo.simaland.service.WarehouseItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "Order Controller", description = "Order Controller")
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final ItemService itemService;
    private final OrderMapper orderMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_ORDERS')")
    @Operation(summary = "Get all orders")
    @ApiResponse(responseCode = "200", description = "Order list", content = @Content)
//    @PreAuthorize()
    ListResponse<OrderResponse> getOrders(PaginationRequest paginationRequest) {

        PageRequest pageRequest = paginationRequest.toPageRequest();
        Page<Order> page = orderService.getOrders(pageRequest);

        return orderMapper.pageToOrderListResponse(page);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ORDER')")
    @Operation(summary = "Create order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Incorrect data format", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found ", content = @Content)
    }
    )
    public OrderResponse createOrder(@RequestBody @Valid CreateOrderRequest request) {
        Order order = orderMapper.mapToOrder(request);
        logger.debug("Mapped order: {}", order);
        order.getOrderItems().forEach(item -> {
            logger.debug("Order item details - Item ID: {}, Quantity: {}", item.getItem().getId(), item.getQuantity());
        });
        return orderMapper.mapToResponse(orderService.createOrder(order));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ORDER')")
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
