package com.itmo.simaland.delegate;

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
    @Autowired
    private final ItemService itemService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("in ItemCreationDelegate");
        String itemName = (String) execution.getVariable("itemName");
        Integer itemPrice = Integer.parseInt((String) execution.getVariable("itemPrice"));
        Item newItem = new Item();
        newItem.setName(itemName);
        newItem.setPrice(itemPrice);

        Item savedItem = itemService.createItem(newItem);

        execution.setVariable("itemId", savedItem.getId());
    }
}