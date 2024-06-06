package com.itmo.simaland.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.itmo.simaland.dto.filter.ItemFilter;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemSelectDelegate implements JavaDelegate {

    @Autowired
    private final ItemService itemService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer minPrice = (Integer) execution.getVariable("minPrice");
        Integer maxPrice = (Integer) execution.getVariable("maxPrice");
        PageRequest pageRequest = PageRequest.of(0, 10);
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setMaxPrice(maxPrice);
        itemFilter.setMinPrice(minPrice);

        Page<Item> items = itemService.getAllItems(pageRequest, itemFilter);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(items);

        JsonValue jsonValue = SpinValues.jsonValue(json).create();
        execution.setVariable("items", jsonValue);
    }
}