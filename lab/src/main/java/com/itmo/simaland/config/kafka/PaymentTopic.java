package com.itmo.simaland.config.kafka;


import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PaymentTopic {

    @Value("${spring.kafka.topic.payment-service-topic.topic-name}")
    private String topicName;
    @Value("${spring.kafka.topic.payment-service-topic.partitions}")
    private int partitions;
    @Value("${spring.kafka.topic.payment-service-topic.replication-factor}")
    private short replicationFactor;

    @Bean
    public NewTopic topic(){

        return new NewTopic(topicName,partitions,  replicationFactor);
    }

}
