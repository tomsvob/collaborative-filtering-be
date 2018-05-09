package app.DTO;

public class RatedFilmDTO extends FilmDTO {
    private Double rating;

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
