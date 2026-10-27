package model.data;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("male"),
    FEMALE("female");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public static Gender fromValue(String gender) {
        if (gender == null) {
            return null;
        }
        if (gender.equalsIgnoreCase("male")) {
            return MALE;
        }
        if (gender.equalsIgnoreCase("female")) {
            return FEMALE;
        }
        throw new IllegalArgumentException("Неизвестный пол: " + gender);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
    
    
}
