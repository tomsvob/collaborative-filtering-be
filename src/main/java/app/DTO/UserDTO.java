package app.DTO;

import app.Enums.UserType;

public class UserDTO {
    Long id;
    UserType userType;
    String username;

    public UserDTO(Long id, UserType userType, String username) {
        this.id = id;
        this.userType = userType;
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
