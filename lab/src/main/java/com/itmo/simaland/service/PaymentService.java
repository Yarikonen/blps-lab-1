package com.itmo.simaland.service;

import com.itmo.simaland.dto.payment.PaymentRequest;
import com.itmo.simaland.model.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final OrderService orderService;

    private final KafkaHandlingService kafkaHandlingService;

    @Value("payment")
    private String topicName;

    public boolean processPayment(PaymentRequest paymentRequest) {
        // имитация
        // в реальном приложении здесь будет интеграция с платежной системой


        log.info("begin");
        Order order = orderService.getOrderById(paymentRequest.getOrderId());
        orderService.updateOrderPaidStatus(order.getId(),true);
        kafkaHandlingService.send(topicName,order.getId().toString(),"Оплачено");
        log.info("start");
        return paymentRequest.getCardNumber() != null && paymentRequest.getCardNumber().length() == 16;
    }
}
