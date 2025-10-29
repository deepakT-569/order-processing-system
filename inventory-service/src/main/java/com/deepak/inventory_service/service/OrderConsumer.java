package com.deepak.inventory_service.service;

import com.deepak.common.event.InventoryEvent;
import com.deepak.common.event.OrderEvent;
import com.deepak.inventory_service.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderConsumer {

//    @KafkaListener(topics = "${app.topic.name}",groupId = "inventory-service")
//    public void consumerOrder(OrderEvent orderEvent){
//        System.out.println("Received order event: "+orderEvent);
//        System.out.println("Updating inventory for item: "+orderEvent
//                .getOrderId());
//    }

    @Value("${app.topic.name}")
    private String topicName;
    private final StockRepository stockRepository;
    private final KafkaTemplate<String, InventoryEvent> kafkaTemplate;

    OrderConsumer(StockRepository stockRepository, KafkaTemplate<String, InventoryEvent> kafkaTemplate){
        this.stockRepository = stockRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order-events",groupId = "inventory-service")
    @Transactional
    public void onOrder(OrderEvent orderEvent){
    var stockOtp = stockRepository.findById(orderEvent.getOrderId());
    InventoryEvent inventoryEvent = new InventoryEvent();
    inventoryEvent.setOrderId(orderEvent.getOrderId());
    inventoryEvent.setProduct(orderEvent.getProduct());
    inventoryEvent.setQuantity(orderEvent.getQuantity());

    if(stockOtp.isPresent() && stockOtp.get().getQuantity() >= orderEvent.getQuantity()){
        var stock = stockOtp.get();
        stock.setQuantity(stock.getQuantity() - orderEvent.getQuantity());
        inventoryEvent.setStatus("RESERVED");
        stockRepository.save(stock);
        inventoryEvent.setTimeStamp(Instant.now());
    }else {
        inventoryEvent.setStatus("REJECTED");
        inventoryEvent.setTimeStamp(Instant.now());
    }
    kafkaTemplate.send(topicName,inventoryEvent.getOrderId(),inventoryEvent);
    }

}
