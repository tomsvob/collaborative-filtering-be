package app.Controllers;

import app.Services.AdminService;
import app.Spearman.SpearmanSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/calculate-recommended")
    public ResponseEntity calculateRecommended(@RequestParam int numOfSimilarUsers, @RequestParam int numOfRecommended, @RequestParam int minNumOfMatches) {
        adminService.calculateRecommended(new SpearmanSettings(numOfSimilarUsers, numOfRecommended, minNumOfMatches));
        return new ResponseEntity(HttpStatus.OK);
    }
}
