package main.java.com.example.marketplace.exceptions;

public final class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }
}
