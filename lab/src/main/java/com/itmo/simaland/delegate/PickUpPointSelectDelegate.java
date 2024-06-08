package com.itmo.simaland.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.itmo.simaland.service.PickUpPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.camunda.spin.plugin.variable.SpinValues;
import org.camunda.spin.plugin.variable.value.JsonValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PickUpPointSelectDelegate implements JavaDelegate {

    private final PickUpPointService pickUpPointService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        var points =pickUpPointService.getAllOfAllPickUpPoints();


        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(points);
        JsonValue jsonValue = SpinValues.jsonValue(json).create();
        delegateExecution.setVariable("points", jsonValue);

        List<String> itemSelections = new ArrayList<>();
        points.forEach(item -> itemSelections.add(item.getId() + ": " + item.getAddress()));
        delegateExecution.setVariable("select-options-points", Spin.JSON(itemSelections));

    }
}
