package com.itmo.simaland.delegate;

import com.itmo.simaland.dto.pickUpPoint.PickUpPointRequest;
import com.itmo.simaland.model.entity.PickUpPoint;
import com.itmo.simaland.service.PickUpPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePickUpPointDelegate implements JavaDelegate {

    private final PickUpPointService pickUpPointService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var pickUp = new PickUpPointRequest(
                (String) delegateExecution.getVariable("address"),
                Long.parseLong((String) delegateExecution.getVariable("warehouseId"))
        );

        pickUpPointService.createPickUpPoint(pickUp);

    }
}
