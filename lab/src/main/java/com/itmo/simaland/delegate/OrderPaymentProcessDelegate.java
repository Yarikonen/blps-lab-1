package com.itmo.simaland.delegate;

import com.itmo.simaland.dto.payment.PaymentRequest;
import com.itmo.simaland.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@AllArgsConstructor
public class OrderPaymentProcessDelegate implements JavaDelegate {


    private PaymentService paymentService;



    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var orderId = Long.parseLong((String) delegateExecution.getVariable("orderId"));
        var cardNumber = (String) delegateExecution.getVariable("cardNumber");
        var cardHolderName = (String) delegateExecution.getVariable("cardHolderName");
        var cvv = (String) delegateExecution.getVariable("cvv");
        var amount  = Double.parseDouble((String) delegateExecution.getVariable("orderTotal"));

        log.info("Executing task {}", delegateExecution.getCurrentActivityId());
        paymentService.processPayment(
            new PaymentRequest(
                orderId,
                cardNumber,
                cardHolderName,
                null,
                cvv,
                amount
            ));
    }
}
