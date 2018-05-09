package app.Services;

import app.DAO.FilmDAO;
import app.DAO.RatingDAO;
import app.DAO.UserDAO;
import app.DTO.RatingDTO;
import app.Models.AppUser;
import app.Models.Film;
import app.Models.Rating;
import app.Spearman.SpearmanRecommender;
import app.Spearman.SpearmanSettings;
import app.Spearman.SpearmanUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AdminService {
    private RatingDAO ratingDAO;
    private UserDAO userDAO;
    private FilmDAO filmDAO;

    @Autowired
    public AdminService(RatingDAO ratingDAO, UserDAO userDAO, FilmDAO filmDAO) {
        this.ratingDAO = ratingDAO;
        this.userDAO = userDAO;
        this.filmDAO = filmDAO;
    }

    public void calculateRecommended(SpearmanSettings spearmanSettings) {
        List<Rating> ratingList = ratingDAO.findAll();
        List<RatingDTO> ratingDTOS = new ArrayList<>();
        for (Rating rating : ratingList) {
            RatingDTO aRating = new RatingDTO(rating.getFilm().getId(), rating.getUser().getId(), rating.getRating());
            ratingDTOS.add(aRating);
        }

        Collection<SpearmanUser> spearmanUsers = SpearmanRecommender.calculateRecommendedMovies(ratingDTOS, spearmanSettings);
        for (SpearmanUser spearmanUser : spearmanUsers) {
            AppUser appUser = userDAO.getOne(spearmanUser.getUserID());
            ArrayList<Long> filmIds = spearmanUser.getRecommendations();
            List<Film> recommendedFilms = filmDAO.findAll(filmIds);
            appUser.setRecommendedFilms(recommendedFilms);
            userDAO.save(appUser);
        }
    }
}
