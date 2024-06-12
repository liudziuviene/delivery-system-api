package org.example.deliverysystemapi.exceptions;

public class DuplicateCourierException extends RuntimeException {
    public DuplicateCourierException(String message) {
        super(message);
    }
}
