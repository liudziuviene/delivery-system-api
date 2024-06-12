package org.example.deliverysystemapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateOrderRequest {
    @Size(min = 5, max = 50, message = "Pickup address must be between 5 and 50")
    private String pickupAddress;
    @Size(min = 5, max = 50, message = "Delivery address must be between 5 and 50")
    private String deliveryAddress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime orderDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deliveryDate;
    @Size(min = 7, max = 20, message = "Status must be between 7 and 20")
    private String status;
    private Long customerId;
}
