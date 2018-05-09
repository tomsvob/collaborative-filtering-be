package app.Services;

import app.DAO.FilmDAO;
import app.DAO.RatingDAO;
import app.DAO.UserDAO;
import app.DTO.RatedFilmDTO;
import app.Models.AppUser;
import app.Models.Film;
import app.Models.Rating;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private UserDAO userDAO;
    private FilmDAO filmDAO;
    private RatingDAO ratingDAO;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public FilmService(UserDAO userDAO, FilmDAO filmDAO, RatingDAO ratingDAO) {
        this.userDAO = userDAO;
        this.filmDAO = filmDAO;
        this.ratingDAO = ratingDAO;
    }

    private List<RatedFilmDTO> enhanceWithRates(List<Film> list, Long uid) {
        List<RatedFilmDTO> ratedFilms = new ArrayList<>();

        for (Film film : list) {
            RatedFilmDTO ratedFilmDTO = modelMapper.map(film, RatedFilmDTO.class);
            if (uid > 0) {
                List<Rating> ratings = ratingDAO.find(film.getId(), uid);
                if (ratings.size() > 0) {
                    ratedFilmDTO.setRating(ratings.get(0).getRating());
                } else {
                    ratedFilmDTO.setRating(0.0);
                }
            } else {
                ratedFilmDTO.setRating(0.0);
            }

            ratedFilms.add(ratedFilmDTO);
        }

        return ratedFilms;
    }

    public List<RatedFilmDTO> getRatedFilms(Long uid) {
        AppUser user = userDAO.getOne(uid);

        List<RatedFilmDTO> ratedFilmDTOs = new ArrayList<>();
        List<Rating> ratings = user.getRatings();
        for (Rating rating : ratings) {
            Film film = rating.getFilm();
            RatedFilmDTO ratedFilmDTO = modelMapper.map(film, RatedFilmDTO.class);
            ratedFilmDTO.setRating(rating.getRating());
            ratedFilmDTOs.add(ratedFilmDTO);
        }

        return ratedFilmDTOs;
    }

    public List<RatedFilmDTO> getRecommendedFilmsForUser(Long uid) {
        List<Film> films = userDAO.findOne(uid).getRecommendedFilms();

        return enhanceWithRates(films, uid);
    }

    public List<RatedFilmDTO> searchFilms(Long uid, String query) {
        List<Film> films = filmDAO.findByNameContaining(query);

        return enhanceWithRates(films, uid);
    }

    public List<RatedFilmDTO> getRandomFilms(Long uid) {
        List<Film> films = filmDAO.findAll().subList(0, 10);

        return enhanceWithRates(films, uid);
    }
}
