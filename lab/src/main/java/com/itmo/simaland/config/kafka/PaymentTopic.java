package com.itmo.simaland.config.kafka;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Configuration
public class PaymentTopic {

    @Value("${spring.kafka.topic.payment-service-topic.topic-name}")
    private String topicName;
    @Value("${spring.kafka.topic.payment-service-topic.partitions}")
    private int partitions;
    @Value("${spring.kafka.topic.payment-service-topic.replication-factor}")
    private short replicationFactor;

    @Value("${spring.kafka.topic.payment-service-topic.min-in-sync-replicas}")
    private short isr;

    @Bean("paymentTopicConfig")
    public NewTopic topic(){
        HashMap<String, String> configs = new HashMap<>();
        configs.put(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG,String.valueOf(isr));

        return new NewTopic(topicName,partitions,  replicationFactor)
                .configs(configs);
    }

}
