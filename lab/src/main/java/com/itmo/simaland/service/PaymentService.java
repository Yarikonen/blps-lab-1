package com.itmo.simaland.service;

import com.itmo.simaland.dto.payment.PaymentRequest;
import com.itmo.simaland.model.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final OrderService orderService;

    private final Producer<String, String> kafkaProducer;

    public boolean processPayment(PaymentRequest paymentRequest) {
        // имитация
        // в реальном приложении здесь будет интеграция с платежной системой


        log.info("begin");
        Order order = orderService.getOrderById(paymentRequest.getOrderId());
        orderService.updateOrderPaidStatus(order.getId(),true);
        log.info("start");
        kafkaProducer.send(new ProducerRecord<String, String>("mtopic", paymentRequest.getCvv(),"Оплачено" ));
        kafkaProducer.close();
        return paymentRequest.getCardNumber() != null && paymentRequest.getCardNumber().length() == 16;
    }
}
