package com.asu.EduMentor.logging;

public class SessionCreationLog implements Loggable {
    long sessionId;

    public SessionCreationLog(long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toLog() {
        return "Session " + sessionId + " created";
    }
}
