package app.DTO;

import app.Enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    Long id;
    UserType userType;
    String username;
}
