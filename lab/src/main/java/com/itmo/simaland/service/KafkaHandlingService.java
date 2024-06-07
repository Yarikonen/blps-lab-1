package com.itmo.simaland.service;


import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaHandlingService {

    private Producer<String,String> kafkaProducer;

    private Admin admin;
    private List<NewTopic> topics;

    @EventListener(ContextRefreshedEvent.class)
    private void createTopics(){
        try {
            log.info("CREATING SMTH");

            CreateTopicsResult result = admin.createTopics(topics);
            Set<Map.Entry<String, KafkaFuture<Void>>> futures = result.values().entrySet();
            for (Map.Entry<String, KafkaFuture<Void>> future: futures
                 ) {
                try {
                    future.getValue().get();
                    log.info("Topic with name "+ future.getKey() + " created");
                }
                catch(ExecutionException|InterruptedException excp) {
                    log.warn( future.getKey() +  " topic creation failed with error: " + excp);

                }

            }

        }
        catch(RuntimeException excp){
            log.error("TOPIC CREATION FAILED WITH" + excp.getMessage());
        }
    }


    public void send(String topicName, String key, String value){
        kafkaProducer.send(new ProducerRecord<String, String>(topicName, key, value ));
        log.info("Message sent");
    }


}
