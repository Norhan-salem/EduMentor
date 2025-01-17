package com.asu.EduMentor.model.profile.template;


import com.asu.EduMentor.model.*;
import java.util.*;

public class AdminProfileTemplate extends ProfileTemplate {

    @Override
    protected List<String> getTypeSpecificRequiredFields() {
        List<String> fields = new ArrayList<>();
        fields.add("status");
        return fields;
    }

    @Override
    protected void setupSpecificFields(User user) {
        if (!(user instanceof Admin admin)) {
            return;
        }

        profileData.put("status", admin.isStatus());
    }

    @Override
    protected boolean validateTypeSpecificField(String key, Object value) {
        if (value == null) return false;

        return switch (key) {
            case "status" -> value instanceof Boolean;
            default -> false;
        };
    }

    @Override
    public boolean addInformation(Map<String, Object> information, User user) {
        if (!(user instanceof Admin admin)) {
            return false;
        }

        try {
            if (information.containsKey("status")) {
                Boolean newStatus = (Boolean) information.get("status");
                admin.setStatus(newStatus);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected User getUserById(int id) {
        try {
            return (Admin) new Admin().read(id);
        } catch (Exception e) {
            return null;
        }
    }
}
