package com.asu.EduMentor.controller.rest.body;


public class SignUpBody {

    private String firstName;
    private String lastName;
    private CredentialsBody credentials;
    private int userType;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        if (!firstName.matches("^[A-Za-z]+$")) {
            throw new IllegalArgumentException("First name should contain only alphabetic characters.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
        if (!lastName.matches("^[A-Za-z]+$")) {
            throw new IllegalArgumentException("Last name should contain only alphabetic characters.");
        }
        this.lastName = lastName;
    }

    public CredentialsBody getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsBody credentials) {
        if (credentials == null) {
            throw new IllegalArgumentException("Credentials cannot be null.");
        }
        this.credentials = credentials;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        if (userType < 0 || userType > 4) {
            throw new IllegalArgumentException("Role must be 0 (Admin), 1 (Mentor), or 2 (Mentee).");
        }
        this.userType = userType;
    }
}
