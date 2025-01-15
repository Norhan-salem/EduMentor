package com.asu.EduMentor.logging;

public class SessionCreationLog implements Loggable {
    String sessionName;

    public SessionCreationLog(String sessionName) {
        this.sessionName = sessionName;
    }

    @Override
    public String toLog() {
        return "Session " + sessionName + " created";
    }
}
