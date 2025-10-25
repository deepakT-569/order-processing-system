package com.deepak.inventory_service.service;

import com.deepak.common.event.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(topics = "${app.topic.name}",groupId = "inventory-service")
    public void consumerOrder(OrderEvent orderEvent){
        System.out.println("Received order event: "+orderEvent);
        System.out.println("Updating inventory for item: "+orderEvent
                .getOrderId());
    }

}
