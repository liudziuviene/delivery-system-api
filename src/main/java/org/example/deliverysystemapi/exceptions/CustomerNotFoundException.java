package org.example.deliverysystemapi.exceptions;

import lombok.Getter;

@Getter
public class CustomerNotFoundException extends RuntimeException {
    private final Long customerId;

    public CustomerNotFoundException(Long customerId) {
        super("Customer not found");
        this.customerId = customerId;
    }
}
