package app.Controllers;

import app.DAO.UserDAO;
import app.DTO.UserDTO;
import app.Models.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class LoginController {
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    protected UserDAO userDAO;
    private ModelMapper modelMapper = new ModelMapper();

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDTO login(@RequestParam Long uid) {
        AppUser user = userDAO.getOne(uid);
        return modelMapper.map(user, UserDTO.class);
    }

}
