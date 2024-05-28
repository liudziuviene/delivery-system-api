package org.example.deliverysystemapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateOrderRequest {
    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 10, max = 50, message = "{validation.constraints.size.message}")
    private final String pickupAddress;
    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 10, max = 50, message = "{validation.constraints.size.message}")
    private final String deliveryAddress;
    @NotNull(message = "{validation.constraints.not.empty.message}")
    private final LocalDateTime orderDate;
    @NotNull(message = "{validation.constraints.not.empty.message}")
    private final LocalDateTime deliveryDate;
    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 7, max = 20, message = "{validation.constraints.size.message}")
    private final String status;
    @NotNull(message = "{validation.constraints.not.empty.message}")
    private final Long customerId;
}
