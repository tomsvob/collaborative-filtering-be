package app.DAO;

import app.Models.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<Film, Long> {

}
