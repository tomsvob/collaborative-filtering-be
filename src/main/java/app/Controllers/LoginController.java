package app.Controllers;

import app.DTO.UserDTO;
import app.Enums.UserType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDTO login(@RequestParam("username") String username) {
        switch (username) {
            case "admin":
                return new UserDTO(UserType.ADMIN, username);
            case "error":
                return null;
            default:
                return new UserDTO(UserType.USER, username);
        }
    }

}
