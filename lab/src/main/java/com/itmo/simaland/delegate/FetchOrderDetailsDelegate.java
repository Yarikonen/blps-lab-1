package com.itmo.simaland.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.itmo.simaland.dto.order.OrderItemDto;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.model.entity.OrderItem;
import com.itmo.simaland.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FetchOrderDetailsDelegate implements JavaDelegate {

    private final OrderService orderService;

    public FetchOrderDetailsDelegate(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Started execution in FetchOrderDetailsDelegate");
        String userId = (String) execution.getVariable("userId");

        Long orderId = (Long) execution.getVariable("orderId");
        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            log.error("No order found for ID: {}", orderId);
            throw new IllegalStateException("No order found for user ID: " + orderId);
        }

        List<OrderItem> items = order.getOrderItems();
        log.info("Order items {}", items.toString());
        List<OrderItemDto> itemDtos = items.stream()
                .map(item -> new OrderItemDto(
                        item.getItem().getName(),
                        item.getQuantity()/2,
                        item.getTotalPrice()
                ))
                .collect(Collectors.toList());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(itemDtos);
        JsonValue jsonValue = SpinValues.jsonValue(json).create();
        execution.setVariable("orderItems", jsonValue);

        execution.setVariable("orderTotal", order.getTotal().toString());
        log.info("Order details set as process variables for user ID: {}", userId);
    }
}

