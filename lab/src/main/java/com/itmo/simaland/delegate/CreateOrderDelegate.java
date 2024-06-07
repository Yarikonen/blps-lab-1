package com.itmo.simaland.delegate;

import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.service.ItemService;
import com.itmo.simaland.service.OrderService;
import com.itmo.simaland.service.WarehouseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateOrderDelegate implements JavaDelegate {

    private final OrderService orderService;
    private final WarehouseService warehouseService;
    private final ItemService itemService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Started execution in CreateOrderDelegate");

        String selectedItem = (String) execution.getVariable("select");
        Integer itemCount = (Integer) execution.getVariable("itemCount");
        String userId = (String) execution.getVariable("userId");
        Long orderId = (Long) execution.getVariable("orderId");

        log.info("Variables - selectedItem: {}, itemCount: {}, userId: {}, orderId: {}", selectedItem, itemCount, userId, orderId);

        String[] parts = selectedItem.split(":");
        Long itemId = Long.parseLong(parts[0]);
        log.info("Parsed itemId: {}", itemId);

        Item item = itemService.getItemById(itemId);
        if (item == null) {
            log.error("Item not found with id: {}", itemId);
            throw new EntityNotFoundException("Item not found with id: " + itemId);
        }
        log.info("Fetched item: {}", item);

        if (orderId == null) {
            log.info("No existing order found, creating a new order");
            Order order = orderService.createOrderWithItem(item, itemCount, Long.valueOf("1"));
            execution.setVariable("orderId", order.getId());
            log.info("Created new order with id: {}", order.getId());
        } else {
            log.info("Adding item to existing order with id: {}", orderId);
            orderService.addItemToOrder(orderId, item, itemCount);
            log.info("Added item to order id: {}", orderId);
        }

        execution.setVariable("approved", true);
        log.info("Set variable 'approved' to true");
    }
}
