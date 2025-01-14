package com.asu.EduMentor.logging;

public class MentorRegisterLog implements Loggable {
    String mentorName;
    long sessionId;

    public MentorRegisterLog(String mentorName, long sessionId) {
        this.mentorName = mentorName;
        this.sessionId = sessionId;
    }

    @Override
    public String toLog() {
        return mentorName + " registered to session " + sessionId + " as a mentor";
    }
}
