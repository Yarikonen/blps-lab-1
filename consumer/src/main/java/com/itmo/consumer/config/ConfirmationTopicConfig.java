package com.itmo.consumer.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ConfirmationTopicConfig {


    @Bean
    public NewTopic confirmationTopic(){
        return TopicBuilder
                .name("confirmation")
                .partitions(2)
                .replicas(3)
                .config(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, "2")
                .build();
    }
}
