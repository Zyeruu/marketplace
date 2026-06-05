package main.java.com.example.marketplace.exceptions;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException(String message) {
        super(message);
    }
}
