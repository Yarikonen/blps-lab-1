package com.itmo.simaland.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;


@Slf4j
@Service
public class KafkaConsumerRunner implements Runnable {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final Consumer<String, String> consumer;

    private final OrderService orderService;
    private final RuntimeService runtimeService;



    public KafkaConsumerRunner(Consumer<String,String> consumer, OrderService orderservice, RuntimeService runtimeService) {
        this.consumer = consumer;
        this.orderService=orderservice;
        this.runtimeService = runtimeService;
    }

    public void run() {
        try {
            consumer.subscribe(Arrays.asList("payment"));
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
                for (ConsumerRecord<String,String> record: records) {

                    log.info(
                            "WINNER WINNER CHICKEN DINNER"
                    );
                    orderService.updateOrderPaidStatus(Long.parseLong(record.value()), true );
                    runtimeService.correlateMessage("Message_Order_Paid");

                }
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            if (!closed.get()) throw e;
        } finally {
            consumer.close();
        }
    }

    // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }
}
