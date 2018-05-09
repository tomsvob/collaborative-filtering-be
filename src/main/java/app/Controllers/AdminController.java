package app.Controllers;

import app.Algorithms.Settings;
import app.Services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    private RatingService ratingService;

    @Autowired
    public AdminController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/calculate-recommended")
    public ResponseEntity calculateRecommended(@RequestParam int numOfSimilarUsers, @RequestParam int numOfRecommended, @RequestParam int minNumOfMatches) {
        ratingService.calculateRecommended(new Settings(numOfSimilarUsers, numOfRecommended, minNumOfMatches));
        return new ResponseEntity(HttpStatus.OK);
    }
}
