package app.Services;

import app.DAO.UserDAO;
import app.DTO.UserDTO;
import app.Models.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDAO userDAO;
    private ModelMapper modelMapper = new ModelMapper();

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDTO getUser(Long uid) {
        AppUser user = userDAO.getOne(uid);
        return modelMapper.map(user, UserDTO.class);
    }
}
