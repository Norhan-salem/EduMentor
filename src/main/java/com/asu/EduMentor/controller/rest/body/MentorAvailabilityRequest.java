package com.asu.EduMentor.controller.rest.body;

import com.asu.EduMentor.model.Availability;
import com.asu.EduMentor.model.Mentor;

public class MentorAvailabilityRequest {
    Mentor mentor;
    Availability availability;

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        if (mentor != null)
            this.mentor = mentor;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        if (availability != null)
            this.availability = availability;
    }
}
