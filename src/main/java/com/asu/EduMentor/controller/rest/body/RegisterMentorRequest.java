package com.asu.EduMentor.controller.rest.body;

import com.asu.EduMentor.model.Mentor;
import com.asu.EduMentor.model.Session;

public class RegisterMentorRequest {
    private Session session;
    private Mentor mentor;

    // Getters and setters
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        if (session != null)
            this.session = session;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        if (mentor != null)
            this.mentor = mentor;
    }
}