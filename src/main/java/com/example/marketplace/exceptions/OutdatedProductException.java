package main.java.com.example.marketplace.exceptions;

public class OutdatedProductException extends RuntimeException {

    public OutdatedProductException(String message) {
        super(message);
    }
}
