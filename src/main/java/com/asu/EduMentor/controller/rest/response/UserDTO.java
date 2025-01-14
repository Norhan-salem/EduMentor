package com.asu.EduMentor.controller.rest.response;

import com.asu.EduMentor.model.User;
import com.asu.EduMentor.model.UserType;

public class UserDTO {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private UserType userType;

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserID(user.getUserID());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUserType(user.getRole());
        return dto;
    }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }
}