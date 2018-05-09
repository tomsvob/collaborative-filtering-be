package app.Services;

import app.DAO.FilmDAO;
import app.DAO.RatingDAO;
import app.DAO.UserDAO;
import app.Models.AppUser;
import app.Models.Film;
import app.Models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private RatingDAO ratingDAO;
    private UserDAO userDAO;
    private FilmDAO filmDAO;

    @Autowired
    public RatingService(RatingDAO ratingDAO, UserDAO userDAO, FilmDAO filmDAO) {
        this.ratingDAO = ratingDAO;
        this.userDAO = userDAO;
        this.filmDAO = filmDAO;
    }

    public void setRating(Long uid, Long fid, Double rating) {
        List<Rating> rates = ratingDAO.find(fid, uid);
        Rating rate;

        if (rates.size() == 0) {
            Film film = filmDAO.getOne(fid);
            AppUser user = userDAO.getOne(uid);
            rate = new Rating(film, user, rating);
        } else {
            rate = rates.get(0);
            rate.setRating(rating);
        }

        ratingDAO.save(rate);
    }
}
