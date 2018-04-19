package app.DAO;

import app.Models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingDAO extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r WHERE r.film.id = :fid AND r.user.id = :uid")
    List<Rating> find(@Param("fid") Long fid, @Param("uid") Long uid);

}
