package com.asu.EduMentor.model.profile.template;

import com.asu.EduMentor.model.User;
import com.asu.EduMentor.model.UserType;

import java.util.List;

public class ProfileTemplateFactory {

    private ProfileTemplateFactory() {
        // Private constructor to prevent instantiation
    }

    /**
     * Creates a profile template based on user type
     * @param userType The type of user
     * @return Appropriate profile template instance
     * @throws IllegalArgumentException if user type is invalid
     */
    public static ProfileTemplate createTemplate(UserType userType) {
        return switch (userType) {
            case MENTOR -> new MentorProfileTemplate();
            case MENTEE -> new MenteeProfileTemplate();
            case ONLINEDONOR -> new OnlineDonorProfileTemplate();
            case ADMIN -> new AdminProfileTemplate();
            default -> throw new IllegalArgumentException("Invalid user type: " + userType);
        };
    }

    /**
     * Creates a profile template for a specific user ID
     * @param userId The ID of the user
     * @return Appropriate profile template instance
     * @throws IllegalArgumentException if user is not found or type is invalid
     */
    public static ProfileTemplate createTemplateForUser(int userId) {
        UserType userType = getUserTypeFromDatabase(userId);
        if (userType == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        return createTemplate(userType);
    }

    private static UserType getUserTypeFromDatabase(int userId) {
        try {
            User user = new User() {
                @Override
                public Object create() { return null; }
                @Override
                public Object update(Object updatedObject) { return null; }
                @Override
                public Object read(int id) { return null; }
                @Override
                public List<Object> readAll() { return null; }
            };

            User foundUser = (User) user.read(userId);
            return foundUser != null ? foundUser.getRole() : null;

        } catch (Exception e) {
            return null;
        }
    }
}