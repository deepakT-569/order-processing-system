package com.deepak.order_service.controller;

import com.deepak.common.event.OrderEvent;
import com.deepak.order_service.dto.OrderDto;
import com.deepak.order_service.model.Order;
import com.deepak.order_service.service.OrderProducer;
import com.deepak.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    public OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    @PostMapping
//    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) {
//        orderService.createOrder(orderDto);
//        return ResponseEntity.ok("Order published Successfully!");
//    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) {
        orderService.createOrder(orderDto);
        return new ResponseEntity<>("Order is saved to Db Successfully!",HttpStatus.CREATED);
    }

}
