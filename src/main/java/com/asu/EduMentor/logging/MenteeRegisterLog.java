package com.asu.EduMentor.logging;

public class MenteeRegisterLog implements Loggable {
    String menteeName;
    long sessionId;

    public MenteeRegisterLog(String menteeName, long sessionId) {
        this.menteeName = menteeName;
        this.sessionId = sessionId;
    }

    @Override
    public String toLog() {
        return menteeName + " registered to session " + sessionId + " as a mentor";
    }
}
