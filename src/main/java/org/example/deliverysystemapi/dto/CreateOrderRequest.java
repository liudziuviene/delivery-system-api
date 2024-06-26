package org.example.deliverysystemapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateOrderRequest {
    @NotBlank(message = "Pickup address must not be blank")
    @Size(min = 5, max = 50, message = "Pickup address must be between 5 and 50")
    private String pickupAddress;
    @NotBlank(message = "Delivery address must not be blank")
    @Size(min = 5, max = 50, message = "Delivery address must be between 5 and 50")
    private String deliveryAddress;
    @NotNull(message = "Order date must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime orderDate;
    @NotNull(message = "Delivery date must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deliveryDate;
    @NotBlank(message = "Status must not be blank")
    @Size(min = 7, max = 20, message = "Status must be between 7 and 20")
    private String status;
    @NotNull(message = "Customer ID must not be blank")
    private Long customerId;
}
