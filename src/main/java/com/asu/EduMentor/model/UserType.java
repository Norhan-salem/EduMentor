package com.asu.EduMentor.model;

public enum UserType {
    ADMIN(1),
    MENTOR(2),
    MENTEE(3),
    ONLINEDONOR(4);

    private final int roleId;

    UserType(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public static UserType fromRoleId(int roleId) {
        for (UserType userType : UserType.values()) {
            if (userType.getRoleId() == roleId) {
                return userType;
            }
        }
        throw new IllegalArgumentException("Invalid Role ID: " + roleId);
    }
}

