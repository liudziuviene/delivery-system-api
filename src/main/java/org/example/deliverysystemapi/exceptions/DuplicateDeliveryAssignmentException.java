package org.example.deliverysystemapi.exceptions;

import lombok.Getter;

@Getter
public class DuplicateDeliveryAssignmentException extends RuntimeException {
    private final Long orderId;

    public DuplicateDeliveryAssignmentException(Long orderId) {
        super("Duplicate delivery assignment found");
        this.orderId = orderId;
    }
}
