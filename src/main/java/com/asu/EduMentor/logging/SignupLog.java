package com.asu.EduMentor.logging;

public class SignupLog implements Loggable {
    String name;

    public SignupLog(String name) {
        this.name = name;
    }

    @Override
    public String toLog() {
        return name + " signed up";
    }
}
