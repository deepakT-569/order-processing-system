package com.deepak.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String orderId;
    private String product;
    private int quantity;
    private String status; //"PENDING","RESERVED","REJECTED","PAID","FAILED"
    private double prize;
    private Instant timeStamp;
}
