package com.itmo.simaland.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.itmo.simaland.controller.OrderController;
import com.itmo.simaland.dto.filter.ItemFilter;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemSelectDelegate implements JavaDelegate {

    private final ItemService itemService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Started execution in ItemSelectDelegate");
        Integer minPrice = (Integer) execution.getVariable("minPrice");
        Integer maxPrice = (Integer) execution.getVariable("maxPrice");
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setMinPrice(minPrice);
        itemFilter.setMaxPrice(maxPrice);

        List<Item> items = itemService.getAllItems(itemFilter);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(items);
        JsonValue jsonValue = SpinValues.jsonValue(json).create();
        execution.setVariable("items", jsonValue);

        List<String> itemSelections = new ArrayList<>();
        items.forEach(item -> itemSelections.add(item.getId() + ": " + item.getName()));
        execution.setVariable("select-options", Spin.JSON(itemSelections));
    }
}