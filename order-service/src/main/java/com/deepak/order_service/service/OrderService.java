package com.deepak.order_service.service;

import com.deepak.common.event.OrderEvent;
import com.deepak.common.event.PaymentEvent;
import com.deepak.order_service.consumer.PaymentConsumer;
import com.deepak.order_service.dto.OrderDto;
import com.deepak.order_service.model.Order;
import com.deepak.order_service.repository.OrderRepository;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProducer orderProducer;
    public void createOrder(OrderDto orderDto){
        String id = UUID.randomUUID().toString();
        Order order = new Order();
        order.setOrderId(id);
        order.setProduct(orderDto.getProduct());
        order.setQuantity(orderDto.getQuantity());
        order.setPrize(orderDto.getPrize());
        order.setCreatedAt(Instant.now());
        order.setStatus("PENDING");
        orderRepository.save(order);
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderId(id);
        orderEvent.setProduct(orderDto.getProduct());
        orderEvent.setQuantity(orderDto.getQuantity());
        orderEvent.setPrize(orderDto.getPrize());
        orderEvent.setTimeStamp(Instant.now());
        orderEvent.setStatus("PENDING");
        orderProducer.sendOrder(orderEvent);
    }
    public void updateStatus(String orderId, String status){
        orderRepository.findById(orderId).ifPresent(value -> {
            value.setStatus(status);
            orderRepository.save(value);
        });
    }
}
