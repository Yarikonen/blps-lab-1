package com.itmo.simaland.delegate;

import com.itmo.simaland.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class OrderPaidDelegate implements JavaDelegate {


    private final OrderService orderService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var a =orderService.checkIfPaid(Long.parseLong((String) delegateExecution.getVariable("orderId")));

        delegateExecution.setVariable("orderPaid", a);
    }
}
