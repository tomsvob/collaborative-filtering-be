package app.Algorithms;

import app.DTO.RatingDTO;

import java.util.ArrayList;

public class SimilarUser {
    private double spearmanRank;
    private User user;

    private ArrayList<RatingDTO> intersected_ratings;
    private ArrayList<RatingDTO> exclusive_ratings;

    public SimilarUser(double spearmanRank, User user, ArrayList<RatingDTO> intersected_ratings, ArrayList<RatingDTO> exclusive_ratings) {
        this.spearmanRank = spearmanRank;
        this.user = user;
        this.intersected_ratings = intersected_ratings;
        this.exclusive_ratings = exclusive_ratings;
    }

    public double getSpearmanRank() {
        return spearmanRank;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<RatingDTO> getIntersected_ratings() {
        return intersected_ratings;
    }

    public ArrayList<RatingDTO> getExclusive_ratings() {
        return exclusive_ratings;
    }

}
