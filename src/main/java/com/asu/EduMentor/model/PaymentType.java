package com.asu.EduMentor.model;

public enum PaymentType {
    VISA(0),
    COURIER(1);

    private final int value;

    PaymentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Static method to get the enum by its integer value
    public static PaymentType fromInt(int i) {
        for (PaymentType type : PaymentType.values()) {
            if (type.getValue() == i) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid payment type: " + i);
    }
}
