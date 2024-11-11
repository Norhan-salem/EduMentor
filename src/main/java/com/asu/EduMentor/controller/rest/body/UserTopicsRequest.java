package com.asu.EduMentor.controller.rest.body;

import com.asu.EduMentor.model.Topics;
import com.asu.EduMentor.model.User;

public class UserTopicsRequest {
    User user;
    Topics topics;

    public User getUser() {
        return user;
    }


    public boolean setUser(User user) {
        if (user != null) {
            this.user = user;
            return true;
        }else{
            return false;
        }
    }

    public Topics getTopics() {
        return topics;
    }

    public boolean setTopics(Topics topics) {
        if (topics != null) {
            this.topics = topics;
            return true;
        }else{
            return false;
        }
    }

}
