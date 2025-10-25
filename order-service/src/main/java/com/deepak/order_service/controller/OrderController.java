package com.deepak.order_service.controller;

import com.deepak.common.event.OrderEvent;
import com.deepak.order_service.service.OrderProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    public final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderEvent orderEvent) {
        orderProducer.sendOrder(orderEvent);
        return ResponseEntity.ok("Order published Successfully!");
    }

}
