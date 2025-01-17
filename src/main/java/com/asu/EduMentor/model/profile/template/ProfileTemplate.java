package com.asu.EduMentor.model.profile.template;


import com.asu.EduMentor.model.User;
import com.asu.EduMentor.model.UserType;
import java.util.*;


public abstract class ProfileTemplate implements IProfileTemplate {
    protected Map<String, Object> profileData;
    protected final List<String> requiredFields;

    protected ProfileTemplate() {
        this.profileData = new HashMap<>();
        this.requiredFields = initializeRequiredFields();
    }

    final protected List<String> initializeRequiredFields() {
        List<String> fields = new ArrayList<>();
        fields.add("firstName");
        fields.add("lastName");
        fields.add("email");
        fields.add("role");
        fields.addAll(getTypeSpecificRequiredFields());
        return fields;
    }

    @Override
    final public Map<String, Object> setupProfile(int id) {
        profileData = new HashMap<>();
        User user = getUserById(id);
        if (user != null) {
            setupCommonFields(user);
            setupSpecificFields(user);
        }
        return new HashMap<>(profileData);
    }

    final protected void setupCommonFields(User user) {
        profileData.put("firstName", user.getFirstName());
        profileData.put("lastName", user.getLastName());
        profileData.put("email", user.getEmail());
        profileData.put("role", user.getRole());
    }

    @Override
    final public boolean updateProfile(int id, Map<String, Object> information) {
        User user = getUserById(id);
        if (user == null) return false;

        try {
            if (!validateAllFields(information)) {
                return false;
            }

            profileData.putAll(information);
            return addInformation(information, user);
        } catch (Exception e) {
            return false;
        }
    }

    final protected boolean validateAllFields(Map<String, Object> information) {
        return information.entrySet().stream()
                .allMatch(entry -> isValidField(entry.getKey(), entry.getValue()));
    }

    @Override
    final public boolean deleteProfileItem(int id, String key) {
        if (isRequiredField(key) || !profileData.containsKey(key)) {
            return false;
        }
        profileData.remove(key);
        return true;
    }

    final protected boolean isRequiredField(String key) {
        return requiredFields.contains(key);
    }

    @Override
    final public List<String> getRequiredFields() {
        return new ArrayList<>(requiredFields);
    }

    @Override
    final public boolean validateProfile(Map<String, Object> profileData) {
        if (profileData == null) return false;

        if (!hasAllRequiredFields(profileData)) {
            return false;
        }

        return validateAllFields(profileData);
    }

    final protected boolean hasAllRequiredFields(Map<String, Object> profileData) {
        return requiredFields.stream()
                .allMatch(field -> profileData.containsKey(field) && profileData.get(field) != null);
    }

    final protected boolean isValidField(String key, Object value) {
        if (isCommonField(key)) {
            return validateCommonField(key, value);
        }
        return validateTypeSpecificField(key, value);
    }

    final protected boolean isCommonField(String key) {
        return key.equals("firstName") ||
                key.equals("lastName") ||
                key.equals("email") ||
                key.equals("role");
    }

    final protected boolean validateCommonField(String key, Object value) {
        if (value == null) return false;

        return switch (key) {
            case "firstName", "lastName", "email" -> value instanceof String && !((String) value).isEmpty();
            case "role" -> value instanceof UserType;
            default -> false;
        };
    }

    // abstract methods to be implemented by concrete classes
    protected abstract List<String> getTypeSpecificRequiredFields();
    protected abstract void setupSpecificFields(User user);
    protected abstract boolean validateTypeSpecificField(String key, Object value);
    protected abstract User getUserById(int id);
}