package main.java.com.example.marketplace.exceptions;

public class EmptyCatalogException extends RuntimeException {

    public EmptyCatalogException(String message) {
        super(message);
    }
}
