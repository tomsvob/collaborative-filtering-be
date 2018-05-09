package app.Algorithms;

public class Rating {


    private int userid;
    private int movieid;
    private double rating;

    public Rating(int userid, int movieid, double rating) {

        this.userid = userid;
        this.movieid = movieid;
        this.rating = rating;
    }

    public int getUserID() {
        return userid;
    }

    public int getMovieID() {
        return movieid;
    }

    public double getRating() {
        return rating;
    }
}
