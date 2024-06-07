package com.itmo.simaland.delegate;

import com.itmo.simaland.model.entity.Warehouse;
import com.itmo.simaland.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import com.itmo.simaland.service.ItemService;
import com.itmo.simaland.model.entity.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ItemCreationDelegate implements JavaDelegate {
    private final ItemService itemService;
    private final WarehouseService warehouseService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("in ItemCreationDelegate");
        String itemName = (String) execution.getVariable("itemName");
        Integer itemPrice = Integer.parseInt((String) execution.getVariable("itemPrice"));
        Integer itemCount = Integer.parseInt((String) execution.getVariable("itemCount"));
        Item newItem = new Item();
        newItem.setName(itemName);
        newItem.setPrice(itemPrice);

        Item savedItem = itemService.createItem(newItem);

        warehouseService.addItemsToWarehouse(1L, savedItem, itemCount);
        execution.setVariable("itemId", savedItem.getId());
    }
}