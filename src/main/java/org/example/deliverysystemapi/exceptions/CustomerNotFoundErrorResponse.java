package org.example.deliverysystemapi.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
@Setter
public class CustomerNotFoundErrorResponse extends BaseErrorResponse {

    public CustomerNotFoundErrorResponse(Long customerId) {
        super(HttpStatus.NOT_FOUND.value(), "Customer with ID " + customerId + " not found");
    }
}
