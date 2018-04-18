package app.DAO;

import app.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<AppUser, Long> {

}
