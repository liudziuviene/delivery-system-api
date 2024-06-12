package org.example.deliverysystemapi.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Getter
@Setter
public class BaseErrorResponse {
    private String message;
    private int status;

    public BaseErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        log.warn("Error occurred: {}", message);
    }

    public BaseErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        log.warn("Error occurred: {}", message);
    }
}
