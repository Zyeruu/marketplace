package main.java.com.example.marketplace.exceptions;

public final class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}