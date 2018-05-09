package app.Spearman;

public class SpearmanSettings {
    private static final int DEFAULT_NUMBER_OF_SIMILAR_USERS = 25;
    private static final int DEFAULT_NUMBER_OF_RECOMMENDATIONS = 10;
    private static final int DEFAULT_NUMBER_OF_REQUIRED_MATCHES = 7;

    private int numberOfSimilarUsers;
    private int numberOfRecommendations;
    private int numberOfRequiredMatches;

    public SpearmanSettings() {
        this.numberOfRecommendations = DEFAULT_NUMBER_OF_RECOMMENDATIONS;
        this.numberOfRequiredMatches = DEFAULT_NUMBER_OF_REQUIRED_MATCHES;
        this.numberOfSimilarUsers = DEFAULT_NUMBER_OF_SIMILAR_USERS;
    }

    public SpearmanSettings(int numberOfSimilarUsers, int numberOfRecommendations, int numberOfRequiredMatches) {
        this.numberOfSimilarUsers = numberOfSimilarUsers;
        this.numberOfRecommendations = numberOfRecommendations;
        this.numberOfRequiredMatches = numberOfRequiredMatches;
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
}
