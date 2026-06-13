package main.java.com.example.marketplace.exceptions;

public final class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String message) {
        super(message);
    }
}
