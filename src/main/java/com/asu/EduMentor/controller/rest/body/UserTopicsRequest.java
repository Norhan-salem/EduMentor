package com.asu.EduMentor.controller.rest.body;

import com.asu.EduMentor.controller.rest.response.UserDTO;
import com.asu.EduMentor.model.Topics;

public class UserTopicsRequest {
    UserDTO userDTO;
    Topics topic;

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

    public Topics getTopic() {
        return topic;
    }

    public boolean setTopics(Topics topics) {
        if (topics != null) {
            this.topic = topics;
            return true;
        }

        return false;
    }

}
