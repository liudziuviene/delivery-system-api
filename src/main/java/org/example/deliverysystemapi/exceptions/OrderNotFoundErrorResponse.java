package org.example.deliverysystemapi.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
@Setter
public class OrderNotFoundErrorResponse extends BaseErrorResponse {

    public OrderNotFoundErrorResponse(Long orderId) {
        super(HttpStatus.NOT_FOUND.value(), "Order with ID " + orderId + " not found");
    }
}
