package app.Controllers;

import app.Services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingController {
    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/update-rating")
    public ResponseEntity setRating(@RequestParam Long uid, @RequestParam Long fid, @RequestParam int rating) {
        ratingService.setRating(uid, fid, rating);
        return new ResponseEntity(HttpStatus.OK);
    }
}
