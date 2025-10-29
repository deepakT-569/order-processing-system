package com.deepak.order_service.consumer;

import com.deepak.common.event.PaymentEvent;
import com.deepak.order_service.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {
    private final OrderService orderService;

    public PaymentConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "payment-events", groupId = "payment-service")
    public void onPayment(PaymentEvent paymentEvent) {
        if ("PAID".equalsIgnoreCase(paymentEvent.getStatus())) {
            orderService.updateStatus(paymentEvent.getOrderId(), "COMPLETED");
        } else {
            orderService.updateStatus(paymentEvent.getOrderId(), "FAILED");
        }
    }
}
