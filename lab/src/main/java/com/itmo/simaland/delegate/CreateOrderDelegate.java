package com.itmo.simaland.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.itmo.simaland.dto.filter.ItemFilter;
import com.itmo.simaland.model.entity.Item;
import com.itmo.simaland.model.entity.Order;
import com.itmo.simaland.service.ItemService;
import com.itmo.simaland.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateOrderDelegate implements JavaDelegate {
    private final OrderService orderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Started execution in CreateOrderDelegate");
        String itemName = (String) execution.getVariable("select");
        Integer itemCount = (Integer) execution.getVariable("itemCount");
        execution.setVariable("approved", true);
    }
}
