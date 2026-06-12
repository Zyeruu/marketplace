package main.java.com.example.marketplace.exceptions;

public class OutdatedStoreNameException extends RuntimeException{

    public OutdatedStoreNameException(String message) {
        super(message);
    }
}
