package com.MomsDeveloper.task3;

public enum Characteristics {
    SOLID("Солидный"),
    SEXY("Привлекательный"),
    PACKED("Упакованный"),
    COOL("Реальный");

    private final String description;

    Characteristics(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
