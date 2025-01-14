package com.asu.EduMentor.controller.rest.response;

import com.asu.EduMentor.model.User;

public class AuthResponse {
    private boolean success;
    private UserDTO user;
    private String message;

    public static AuthResponse success(User user) {
        AuthResponse response = new AuthResponse();
        response.setSuccess(true);
        response.setUser(UserDTO.fromUser(user));
        return response;
    }

    public static AuthResponse error(String message) {
        AuthResponse response = new AuthResponse();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}