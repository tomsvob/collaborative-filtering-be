package app.Controllers;

import app.DAO.FilmDAO;
import app.DAO.RatingDAO;
import app.DAO.UserDAO;
import app.Models.AppUser;
import app.Models.Film;
import app.Models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RatingController {
    @Autowired
    RatingDAO ratingDAO;
    @Autowired
    FilmDAO filmDAO;
    @Autowired
    UserDAO userDAO;

    @PostMapping("/update-rating")
    public ResponseEntity setRating(@RequestParam Long uid, @RequestParam Long fid, @RequestParam int rating) {
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
        return new ResponseEntity(HttpStatus.OK);
    }
}
