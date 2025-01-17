package com.asu.EduMentor.model.profile.template;


import com.asu.EduMentor.model.Availability;
import com.asu.EduMentor.model.Mentor;
import com.asu.EduMentor.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MentorProfileTemplate extends ProfileTemplate {

    @Override
    protected List<String> getTypeSpecificRequiredFields() {
        List<String> fields = new ArrayList<>();
        fields.add("totalHours");
        fields.add("availabilities");
        return fields;
    }

    @Override
    protected void setupSpecificFields(User user) {
        if (!(user instanceof Mentor mentor)) {
            return;
        }

        profileData.put("totalHours", mentor.getTotalHours());
        profileData.put("availabilities", mentor.getMentorAvailabilities());
        profileData.put("givenSessions", mentor.getGivenSessions());
    }

    @Override
    protected boolean validateTypeSpecificField(String key, Object value) {
        if (value == null) return false;

        return switch (key) {
            case "totalHours" -> value instanceof Double && (Double) value >= 0;
            case "availabilities", "givenSessions" -> value instanceof List<?>;
            default -> false;
        };
    }

    @Override
    public boolean addInformation(Map<String, Object> information, User user) {
        if (!(user instanceof Mentor mentor)) {
            return false;
        }

        try {
            if (information.containsKey("availability")) {
                Availability newAvailability = (Availability) information.get("availability");
                return mentor.addAvailability(newAvailability);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected User getUserById(int id) {
        try {
            return (Mentor) new Mentor().read(id);
        } catch (Exception e) {
            return null;
        }
    }
}