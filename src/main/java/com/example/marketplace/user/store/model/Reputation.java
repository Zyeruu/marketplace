package main.java.com.example.marketplace.user.store.model;

import main.java.com.example.marketplace.shared.model.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Reputation {

    private final List<Review> reviewList;
    private float reputationRating;
    private int totalReview;
    private int totalRating;
    private int total1Star;
    private int total2Stars;
    private int total3Stars;
    private int total4Stars;
    private int total5Stars;

    public Reputation() {
        this.reviewList = new ArrayList<>();
        this.reputationRating = 0;
        this.totalReview = 0;
        this.totalRating = 0;
        this.total1Star = 0;
        this.total2Stars = 0;
        this.total3Stars = 0;
        this.total4Stars = 0;
        this.total5Stars = 0;
    }

    public Reputation(Reputation reputation) {
        this.reviewList = reputation.getReviewList().stream().map(Review::new).collect(Collectors.toList());
        this.reputationRating = reputation.getReputationRating();
        this.totalReview = reputation.getTotalReviews();
        this.totalRating = reputation.getTotalRating();
        this.total1Star = reputation.getTotal1Star();
        this.total2Stars = reputation.getTotal2Stars();
        this.total3Stars = reputation.getTotal3Stars();
        this.total4Stars = reputation.getTotal4Stars();
        this.total5Stars = reputation.getTotal5Stars();
    }

    public void updateReputation() {

        reputationRating = 0;
        totalRating = 0;
        total1Star = 0;
        total2Stars = 0;
        total3Stars = 0;
        total4Stars = 0;
        total5Stars = 0;

        for (Review review : reviewList) {
            totalRating += review.getRating();

            switch (review.getRating()) {
                case 1 -> total1Star++;
                case 2 -> total2Stars++;
                case 3 -> total3Stars++;
                case 4 -> total4Stars++;
                case 5 -> total5Stars++;
            }
        }

        totalReview = reviewList.size();
        reputationRating = (float) totalRating / totalReview;
    }

    // Getters
    public List<Review> getReviewList() {
        return reviewList;
    }

    public float getReputationRating() {
        return reputationRating;
    }

    public int getTotalReviews() {
        return totalReview;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public int getTotal1Star() {
        return total1Star;
    }

    public int getTotal2Stars() {
        return total2Stars;
    }

    public int getTotal3Stars() {
        return total3Stars;
    }

    public int getTotal4Stars() {
        return total4Stars;
    }

    public int getTotal5Stars() {
        return total5Stars;
    }
}
