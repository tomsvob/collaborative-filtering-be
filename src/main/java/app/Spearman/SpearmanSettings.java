package app.Spearman;

public class SpearmanSettings {
    private static final int DEFAULT_NUMBER_OF_SIMILAR_USERS = 25;
    private static final int DEFAULT_NUMBER_OF_RECOMMENDATIONS = 10;
    private static final int DEFAULT_NUMBER_OF_REQUIRED_MATCHES = 7;
    private static final int DEFAULT_NUMBER_OF_RATINGS_FROM_SIMILAR_USERS = 7;
    private static final double DEFAULT_SIMILARITY_THRESHOLD = 0.5;

    private int numberOfSimilarUsers;
    private int numberOfRecommendations;
    private int numberOfRequiredMatches;
    private int numberOfRequiredRatingsFromSimilarUsers;
    private double similarityThreshold;

    public SpearmanSettings() {
        this.numberOfRecommendations = DEFAULT_NUMBER_OF_RECOMMENDATIONS;
        this.numberOfRequiredMatches = DEFAULT_NUMBER_OF_REQUIRED_MATCHES;
        this.numberOfSimilarUsers = DEFAULT_NUMBER_OF_SIMILAR_USERS;
        this.numberOfRequiredRatingsFromSimilarUsers = DEFAULT_NUMBER_OF_RATINGS_FROM_SIMILAR_USERS;
        this.similarityThreshold = DEFAULT_SIMILARITY_THRESHOLD;
    }

    public SpearmanSettings(int numberOfSimilarUsers, int numberOfRecommendations, int numberOfRequiredMatches, int numberOfRequiredRatingsFromSimilarUsers, double similarityThreshold) {
        this.numberOfSimilarUsers = numberOfSimilarUsers;
        this.numberOfRecommendations = numberOfRecommendations;
        this.numberOfRequiredMatches = numberOfRequiredMatches;
        this.numberOfRequiredRatingsFromSimilarUsers = numberOfRequiredRatingsFromSimilarUsers;
        this.similarityThreshold = similarityThreshold;
    }

    int getNumberOfSimilarUsers() {
        return numberOfSimilarUsers;
    }

    int getNumberOfRecommendations() {
        return numberOfRecommendations;
    }

    int getNumberOfRequiredMatches() {
        return numberOfRequiredMatches;
    }

    int getNumberOfRequiredRatingsFromSimilarUsers() {
        return numberOfRequiredRatingsFromSimilarUsers;
    }

    public double getSimilarityThreshold() {
        return similarityThreshold;
    }
}
