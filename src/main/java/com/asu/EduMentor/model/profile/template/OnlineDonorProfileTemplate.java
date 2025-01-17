package com.asu.EduMentor.model.profile.template;


import com.asu.EduMentor.model.*;
import java.util.*;

public class OnlineDonorProfileTemplate extends ProfileTemplate {

    @Override
    protected List<String> getTypeSpecificRequiredFields() {
        List<String> fields = new ArrayList<>();
        fields.add("numberOfDonations");
        return fields;
    }

    @Override
    protected void setupSpecificFields(User user) {
        if (!(user instanceof OnlineDonor donor)) {
            return;
        }

        profileData.put("numberOfDonations", donor.getNumberOfDonations());
        profileData.put("donations", donor.getDonations());
    }

    @Override
    protected boolean validateTypeSpecificField(String key, Object value) {
        if (value == null) return false;

        return switch (key) {
            case "numberOfDonations" -> value instanceof Integer && (Integer) value >= 0;
            case "donations" -> value instanceof List<?>;
            default -> false;
        };
    }

    @Override
    public boolean addInformation(Map<String, Object> information, User user) {
        if (!(user instanceof OnlineDonor donor)) {
            return false;
        }

        try {
            if (information.containsKey("numberOfDonations")) {
                Integer newDonations = (Integer) information.get("numberOfDonations");
                donor.setNumberOfDonations(newDonations);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected User getUserById(int id) {
        try {
            return (OnlineDonor) new OnlineDonor().read(id);
        } catch (Exception e) {
            return null;
        }
    }
}