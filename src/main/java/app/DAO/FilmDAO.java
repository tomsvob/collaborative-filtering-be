package app.DAO;

import app.Models.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmDAO extends JpaRepository<Film, Long> {

    List<Film> findByNameContaining(String query);
}
