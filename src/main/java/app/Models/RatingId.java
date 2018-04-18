package app.Models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RatingId implements Serializable {
    @Column(name = "app_user_id", nullable = false)
    private long appUserId;
    @Column(name = "film_id", nullable = false)
    private long filmId;

    public RatingId() {
    }

    public RatingId(long appUserId, long filmId) {
        this.appUserId = appUserId;
        this.filmId = filmId;
    }

    public long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(long appUserId) {
        this.appUserId = appUserId;
    }

    public long getFilmId() {
        return filmId;
    }

    public void setFilmId(long filmId) {
        this.filmId = filmId;
    }
}