package app.Services;

import app.Algorithms.ReaderModule;
import app.Algorithms.Settings;
import app.Algorithms.User;
import app.DAO.FilmDAO;
import app.DAO.RatingDAO;
import app.DAO.UserDAO;
import app.DTO.RatingDTO;
import app.Models.AppUser;
import app.Models.Film;
import app.Models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void calculateRecommended(Settings settings) {
        List<Rating> ratingList = ratingDAO.findAll();
        List<RatingDTO> ratingDTOS = new ArrayList<>();
        for (Rating rating : ratingList) {
            RatingDTO aRating = new RatingDTO(rating.getFilm().getId(), rating.getUser().getId(), rating.getRating());
            ratingDTOS.add(aRating);
        }
        ArrayList<User> users = ReaderModule.calculateRecommendedMovies(ratingDTOS, settings);
        for (User user : users) {
            AppUser appUser = userDAO.getOne(user.getUserID());
            ArrayList<Long> filmIds = user.getRecommendations();
            List<Film> recommendedFilms = filmDAO.findAll(filmIds);
            appUser.setRecommendedFilms(recommendedFilms);
            userDAO.save(appUser);
        }
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
