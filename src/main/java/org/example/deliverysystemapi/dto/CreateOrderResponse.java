package org.example.deliverysystemapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateOrderResponse {
    private Long id;
    private String pickupAddress;
    private String deliveryAddress;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private String status;
    private Long customerId;
}
