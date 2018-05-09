package app.Controllers;

import app.DTO.SpearmanStatsDTO;
import app.Services.AdminService;
import app.Spearman.SpearmanSettings;
import org.springframework.beans.factory.annotation.Autowired;
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
    public SpearmanStatsDTO calculateRecommended(@RequestParam int numOfSimilarUsers, @RequestParam int numOfRecommended, @RequestParam int minNumOfMatches) {
        return adminService.calculateRecommended(new SpearmanSettings(numOfSimilarUsers, numOfRecommended, minNumOfMatches));
    }
}
