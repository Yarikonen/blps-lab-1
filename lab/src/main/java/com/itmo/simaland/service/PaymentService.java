package com.itmo.simaland.service;

import com.itmo.simaland.dto.payment.PaymentRequest;
import com.itmo.simaland.model.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final OrderService orderService;

    private final KafkaConsumerRunner kafkaConsumerRunner;

    private final KafkaHandlingService kafkaHandlingService;

    private final Consumer<String, String> kafkaConsumer;

    @Value("payment")
    private String topicName;

    public String processPayment(PaymentRequest paymentRequest) {
        // имитация
        // в реальном приложении здесь будет интеграция с платежной системой


        log.info("begin");
//        Order order = orderService.getOrderById(paymentRequest.getOrderId());
//        orderService.updateOrderPaidStatus(paymentRequest.getOrderId(),true);
        kafkaHandlingService.send(topicName,paymentRequest.getOrderId().toString(),"Оплачено");
        log.info("start");
        return "Payment process proceed";


    }

    @EventListener(ApplicationReadyEvent.class)
    public void consumer(){
        kafkaConsumerRunner.shutdown();


    }
}
