package app.Controllers;

import app.DTO.UserDTO;
import app.Enums.UserType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class LoginController {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDTO login(@RequestParam("username") String username) {
        switch (username) {
            case "admin":
                return new UserDTO(counter.incrementAndGet(), UserType.ADMIN, username);
            case "error":
                return null;
            default:
                return new UserDTO(counter.incrementAndGet(), UserType.USER, username);
        }
    }

}
