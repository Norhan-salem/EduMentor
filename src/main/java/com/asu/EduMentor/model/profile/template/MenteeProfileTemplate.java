package com.asu.EduMentor.model.profile.template;


import com.asu.EduMentor.model.Mentee;
import com.asu.EduMentor.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenteeProfileTemplate extends ProfileTemplate {

    @Override
    protected List<String> getTypeSpecificRequiredFields() {
        List<String> fields = new ArrayList<>();
        fields.add("numberOfAttendedSessions");
        fields.add("learningHours");
        return fields;
    }

    @Override
    protected void setupSpecificFields(User user) {
        if (!(user instanceof Mentee mentee)) {
            return;
        }

        profileData.put("numberOfAttendedSessions", mentee.getNumberOfAttendedSessions());
        profileData.put("learningHours", mentee.getLearningHours());
        profileData.put("attendedSessions", mentee.getAttendedSessions());
    }

    @Override
    protected boolean validateTypeSpecificField(String key, Object value) {
        if (value == null) return false;

        return switch (key) {
            case "numberOfAttendedSessions" -> value instanceof Integer && (Integer) value >= 0;
            case "learningHours" -> value instanceof Double && (Double) value >= 0;
            case "attendedSessions" -> value instanceof List<?>;
            default -> false;
        };
    }

    @Override
    public boolean addInformation(Map<String, Object> information, User user) {
        if (!(user instanceof Mentee mentee)) {
            return false;
        }

        try {
            if (information.containsKey("learningHours")) {
                Double newHours = (Double) information.get("learningHours");
                mentee.setLearningHours(newHours);
            }

            if (information.containsKey("numberOfAttendedSessions")) {
                Integer newSessions = (Integer) information.get("numberOfAttendedSessions");
                mentee.setNumberOfAttendedSessions(newSessions);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected User getUserById(int id) {
        try {
            return (Mentee) new Mentee().read(id);
        } catch (Exception e) {
            return null;
        }
    }
}