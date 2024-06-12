package org.example.deliverysystemapi.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
@Setter
public class CourierNotFoundErrorResponse extends BaseErrorResponse {

    public CourierNotFoundErrorResponse(Long courierId) {
        super(HttpStatus.NOT_FOUND.value(), "Courier with ID " + courierId + " not found");
    }
}
