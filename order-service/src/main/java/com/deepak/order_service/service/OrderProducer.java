package com.deepak.order_service.service;

import com.deepak.common.event.OrderEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${app.topic.name}")
    private String topicName;

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderEvent orderEvent) {
        kafkaTemplate.send(topicName, orderEvent.getOrderId(), orderEvent);
//        System.out.println("Order send to kafka topic '" + topicName + "': " + orderEvent);
    }
}
