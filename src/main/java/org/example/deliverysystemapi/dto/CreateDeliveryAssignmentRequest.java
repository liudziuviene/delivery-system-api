package org.example.deliverysystemapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateDeliveryAssignmentRequest {

    @NotNull(message = "{validation.constraints.not.empty.message}")
    private final LocalDateTime date;
    @NotNull(message = "{validation.constraints.not.empty.message}")
    private final Long orderId;
    @NotNull(message = "{validation.constraints.not.empty.message}")
    private final Long courierId;
}
