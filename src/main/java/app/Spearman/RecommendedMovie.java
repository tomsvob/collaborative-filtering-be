package app.Spearman;

class RecommendedMovie {

    long movieID;
    double aggregatedRating;

    RecommendedMovie(long movieID, double aggregatedRating) {
        this.movieID = movieID;
        this.aggregatedRating = aggregatedRating;
    }
}