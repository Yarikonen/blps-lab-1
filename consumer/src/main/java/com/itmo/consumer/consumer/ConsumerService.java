package com.itmo.consumer.consumer;

import com.itmo.consumer.model.Payment;
import com.itmo.consumer.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@AllArgsConstructor
public class ConsumerService {

    private KafkaTemplate<String, String> template;
    private PaymentRepository paymentRepository;

    private KafkaTransactionManager<String, String> manager;


    @Transactional
    @KafkaListener(topics = "confirmation", groupId = "consumer-1")
    public void listenGroupFoo(String message) {
        paymentRepository.save(new Payment(Long.parseLong(message), "YOO"));
        template.send("payment", message);
        System.out.println("Received Message in group foo: " + message);
    }
}
