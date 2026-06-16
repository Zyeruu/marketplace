package main.java.com.example.marketplace.user.dto;

public record UpdateReviewRequest(String reviewId, String message, Boolean deleteMessage, Integer rating){

}
