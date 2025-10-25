package com.deepak.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEvent {
    private String orderId;
    private String product;
    private int quantity;
    private String status; //"RESERVED","REJECTED"
    private Instant timeStamp;
}
