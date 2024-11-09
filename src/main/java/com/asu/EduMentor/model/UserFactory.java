package com.asu.EduMentor.model;

public class UserFactory {

    public static User createUser(userType type, String firstName, String lastName, String email, String password)
    {
        switch (type){
            case MENTOR:
                return new Mentor(firstName, lastName, email, password);
            case MENTEE:
                return new Mentee(firstName, lastName, email, password);
            case ADMIN:
                return new Admin(firstName, lastName, email, password);
            case ONLINEDONOR:
                return new OnlineDonor(firstName, lastName, email, password);
            default:
                throw new IllegalArgumentException("Invalid user type: " + type);
        }
    }
}
