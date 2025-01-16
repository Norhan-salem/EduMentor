package com.asu.EduMentor.controller.rest.body;
import com.asu.EduMentor.controller.rest.response.UserDTO;
import com.asu.EduMentor.model.Session;

public class RegisterMentorRequest {
    private Session session;
    private UserDTO mentor;

    // Getters and setters
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        if (session != null)
            this.session = session;
    }

    public UserDTO getMentor() {
        return mentor;
    }

    public void setMentor(UserDTO mentor) {
        if (mentor != null)
            this.mentor = mentor;
    }
}