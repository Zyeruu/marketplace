package main.java.com.example.marketplace.exceptions;

public final class EmptyCartException extends RuntimeException {

    public EmptyCartException(String message) {
        super(message);
    }
}
