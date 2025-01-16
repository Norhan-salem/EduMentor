package com.asu.EduMentor.controller.rest.body;

import com.asu.EduMentor.controller.rest.response.UserDTO;
import com.asu.EduMentor.model.Topics;
import com.asu.EduMentor.model.User;

public class UserTopicsRequest {
    UserDTO userDTO;
    Topics topics;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public boolean setUser(UserDTO user) {
        if (user != null) {
            this.userDTO = user;
            return true;
        }

        return false;
    }

    public Topics getTopics() {
        return topics;
    }

    public boolean setTopics(Topics topics) {
        if (topics != null) {
            this.topics = topics;
            return true;
        }

        return false;
    }

}
