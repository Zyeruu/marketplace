package main.java.com.example.marketplace.shared.model;

import java.time.LocalDateTime;

public final class Review {

    private final String id;
    private String message;
    private int rating;
    private final LocalDateTime date;

    public Review(String id, String message, int rating) {
        this.id = id;
        this.message = message;
        this.rating = rating;
        this.date = LocalDateTime.now();
    }

    public Review(Review review) {
        this.id = review.getId();
        this.message = review.getMessage();;
        this.rating = review.getRating();
        this.date = review.getDate();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getRating() {
        return rating;
    }

    public LocalDateTime getDate() {
        return date;
    }

    // Setters
    public void setMessage(String message) {
        this.message = message;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
