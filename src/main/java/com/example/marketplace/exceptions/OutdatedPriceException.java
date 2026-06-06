package main.java.com.example.marketplace.exceptions;

public class OutdatedPriceException extends RuntimeException {

    public OutdatedPriceException(String message) {
        super(message);
    }
}
