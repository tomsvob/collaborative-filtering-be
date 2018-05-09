package app.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.concurrent.atomic.AtomicReference;

@Entity
public class Rating {
    static private final AtomicReference<Long> counter = new AtomicReference<>(200000L);

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser user;
    private Double rating;

    public Rating() {

    }

    public Rating(Double rating) {
        this.rating = rating;
    }

    public Rating(Film film, AppUser user, Double rating) {
        this.id = counter.getAndSet(counter.get() + 1);
        this.film = film;
        this.user = user;
        this.rating = rating;
    }

    public Rating(Long id, Film film, AppUser user, Double rating) {
        this.id = id;
        this.film = film;
        this.user = user;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
