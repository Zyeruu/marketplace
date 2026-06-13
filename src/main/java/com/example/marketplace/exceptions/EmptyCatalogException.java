package main.java.com.example.marketplace.exceptions;

public final class EmptyCatalogException extends RuntimeException {

    public EmptyCatalogException(String message) {
        super(message);
    }
}
