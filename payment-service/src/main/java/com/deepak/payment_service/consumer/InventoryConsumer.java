package com.deepak.payment_service.consumer;

import com.deepak.common.event.InventoryEvent;
import com.deepak.common.event.PaymentEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class InventoryConsumer {

    @Value("${app.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public InventoryConsumer(KafkaTemplate<String, PaymentEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    @KafkaListener(topics = "${app.topic.name}", groupId = "payment-service")
    public void onInventoryEvent(InventoryEvent inventoryEvent){
        String status;
        if("RESERVED".equalsIgnoreCase(inventoryEvent.getStatus())){
//            simulate payment success.
            status = (new Random().nextInt(100) > 90) ? "PAID" : "FAILED";
        }else{
            status = "FAILED";
        }
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setOrderId(inventoryEvent.getOrderId());
        paymentEvent.setStatus(status);
        paymentEvent.setTimeStamp(Instant.now());
        kafkaTemplate.send(topicName,paymentEvent.getOrderId(),paymentEvent);
    }

}
