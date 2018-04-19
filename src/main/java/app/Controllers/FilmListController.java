package app.Controllers;

import app.DAO.FilmDAO;
import app.DAO.RatingDAO;
import app.DAO.UserDAO;
import app.DTO.RatedFilmDTO;
import app.Models.AppUser;
import app.Models.Film;
import app.Models.Rating;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmListController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    FilmDAO filmDAO;
    @Autowired
    RatingDAO ratingDAO;
    private ModelMapper modelMapper = new ModelMapper();

    private List<RatedFilmDTO> enhanceWithRates(List<Film> list, Long uid) {
        List<RatedFilmDTO> ratedFilms = new ArrayList<>();

        for (Film film : list) {
            RatedFilmDTO ratedFilmDTO = modelMapper.map(film, RatedFilmDTO.class);
            if (uid > 0) {
                List<Rating> ratings = ratingDAO.find(film.getId(), uid);
                if (ratings.size() > 0) {
                    ratedFilmDTO.setRating(ratings.get(0).getRating());
                } else {
                    ratedFilmDTO.setRating(0);
                }
            } else {
                ratedFilmDTO.setRating(0);
            }

            ratedFilms.add(ratedFilmDTO);
        }

        return ratedFilms;
    }

    @GetMapping("/film/list")
    public List<RatedFilmDTO> randomFilms(@RequestParam Long uid) {
        List<Film> films = filmDAO.findAll().subList(0, 10);

        return enhanceWithRates(films, uid);
    }

    @PostMapping("/film/search")
    public List<RatedFilmDTO> searchFilms(@RequestParam Long uid, @RequestParam String query) {
        List<Film> films = filmDAO.findByNameContaining(query);

//        Type listType = new TypeToken<List<FilmDTO>>() {}.getType();
//        return modelMapper.map(films, listType);
        return enhanceWithRates(films, uid);
    }

    @PostMapping("/film/recommended")
    public List<RatedFilmDTO> recommendedFilms(@RequestParam Long uid) {
        List<Film> films = filmDAO.findAll().subList(10, 20);

        return enhanceWithRates(films, uid);
    }

    @PostMapping("/film/rated")
    public List<RatedFilmDTO> getRatedFilms(@RequestParam Long uid) {
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

}