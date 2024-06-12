package org.example.deliverysystemapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateDeliveryAssignmentRequest {
    @NotNull(message = "Date must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
    @NotNull(message = "Order ID must not be blank")
    private Long orderId;
    @NotNull(message = "Courier ID must not be blank")
    private Long courierId;
}
