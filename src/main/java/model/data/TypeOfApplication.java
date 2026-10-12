package model.data;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeOfApplication {
    DEATH("Получение свидетельства о смерти"),
    BIRTH("Получение свидетельства о рождении"),
    WEDDING("Получение свидетельства о браке");

    private final String value;

    TypeOfApplication(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
