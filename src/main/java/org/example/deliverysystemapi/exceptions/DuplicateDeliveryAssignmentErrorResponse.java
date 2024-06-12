package org.example.deliverysystemapi.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
@Setter
public class DuplicateDeliveryAssignmentErrorResponse extends BaseErrorResponse {
    private final Long orderId;

    public DuplicateDeliveryAssignmentErrorResponse(Long orderId) {
        super(HttpStatus.CONFLICT.value(), "Delivery assignment for this order ID already exists");
        this.orderId = orderId;
    }
}
