package model.data;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionForApplication {
    APPROVE("approved"),
    REJECT("rejected");

    private final String value;

    ActionForApplication(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
