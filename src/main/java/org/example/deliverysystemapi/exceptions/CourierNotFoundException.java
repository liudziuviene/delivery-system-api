package org.example.deliverysystemapi.exceptions;

import lombok.Getter;

@Getter
public class CourierNotFoundException extends RuntimeException {
    private final Long courierId;

    public CourierNotFoundException(Long courierId) {
        super("Courier not found");
        this.courierId = courierId;
    }
}
