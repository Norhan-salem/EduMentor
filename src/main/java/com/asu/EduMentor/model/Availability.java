package com.asu.EduMentor.model;

import java.sql.Timestamp;

public class Availability {

    private Timestamp time;
    private double duration;
    private boolean isDeleted;


    public Availability(Timestamp time, double duration) {
        this.time = time;
        this.duration = duration;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
