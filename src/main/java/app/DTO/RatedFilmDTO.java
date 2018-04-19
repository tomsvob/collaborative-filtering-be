package app.DTO;

public class RatedFilmDTO extends FilmDTO {
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
