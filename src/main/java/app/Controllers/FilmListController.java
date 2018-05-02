package app.Controllers;

import app.DTO.RatedFilmDTO;
import app.Services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilmListController {

    private FilmService filmService;

    @Autowired
    public FilmListController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/film/list")
    public List<RatedFilmDTO> randomFilms(@RequestParam Long uid) {
        return filmService.getRandomFilms(uid);
    }

    @PostMapping("/film/search")
    public List<RatedFilmDTO> searchFilms(@RequestParam Long uid, @RequestParam String query) {
        return filmService.searchFilms(uid, query);
    }

    @PostMapping("/film/recommended")
    public List<RatedFilmDTO> recommendedFilms(@RequestParam Long uid) {
        return filmService.getRecommendedFilmsForUser(uid);
    }

    @PostMapping("/film/rated")
    public List<RatedFilmDTO> getRatedFilms(@RequestParam Long uid) {
        return filmService.getRatedFilms(uid);
    }

}