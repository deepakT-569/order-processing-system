package com.deepak.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
    private String orderId;
    private String status; //"PAID","FAILED"
    private Instant timeStamp;
}
