package com.asu.EduMentor.model;

public class UserFactory {

    public static User createUser(UserType type, String firstName, String lastName, String email, String password) {
        return switch (type) {
            case MENTOR -> new Mentor(firstName, lastName, email, password);
            case MENTEE -> new Mentee(firstName, lastName, email, password);
            case ADMIN -> new Admin(firstName, lastName, email, password);
            case ONLINEDONOR -> new OnlineDonor(firstName, lastName, email, password);
            default -> throw new IllegalArgumentException("Invalid user type: " + type);
        };
    }
}
