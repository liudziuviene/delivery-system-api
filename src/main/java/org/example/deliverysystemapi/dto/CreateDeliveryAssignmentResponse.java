package org.example.deliverysystemapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateDeliveryAssignmentResponse {
    private Long id;
    private LocalDateTime date;
    private Long orderId;
    private Long courierId;
}
