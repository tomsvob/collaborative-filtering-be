package app.Spearman;

import app.DTO.RatingDTO;

import java.util.ArrayList;

public class SimilarUser {
    private double spearmanRank;
    private SpearmanUser spearmanUser;

    private ArrayList<RatingDTO> exclusive_ratings;

    public SimilarUser(double spearmanRank, SpearmanUser spearmanUser, ArrayList<RatingDTO> exclusive_ratings) {
        this.spearmanRank = spearmanRank;
        this.spearmanUser = spearmanUser;
        this.exclusive_ratings = exclusive_ratings;
    }

    public double getSpearmanRank() {
        return spearmanRank;
    }

    public SpearmanUser getSpearmanUser() {
        return spearmanUser;
    }

    public ArrayList<RatingDTO> getExclusive_ratings() {
        return exclusive_ratings;
    }

}
