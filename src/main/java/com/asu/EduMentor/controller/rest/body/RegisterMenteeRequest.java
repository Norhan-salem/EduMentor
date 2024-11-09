package com.asu.EduMentor.controller.rest.body;

import com.asu.EduMentor.model.Mentee;
import com.asu.EduMentor.model.Session;

public class RegisterMenteeRequest {
    private Session session;
    private Mentee mentee;

    // Getters and setters
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        if (session != null)
            this.session = session;
    }

    public Mentee getMentee() {
        return mentee;
    }

    public void setMentee(Mentee mentee) {
        if (mentee != null)
            this.mentee = mentee;
    }
}
