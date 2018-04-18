package app.DAO;

import app.Models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingDAO extends JpaRepository<Rating, Long> {

}
