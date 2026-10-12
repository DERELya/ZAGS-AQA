package model.data;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusOfApplication {
    APPROVED("approved"),
    REJECTED("rejected"),
    UNDER_CONSIDERATION("under consideration");

    private final String value;

    StatusOfApplication(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
