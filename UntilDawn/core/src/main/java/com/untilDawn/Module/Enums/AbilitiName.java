package com.untilDawn.Module.Enums;

public enum AbilitiName {
    VITALITY("Vitality"),
    DAMAGER("Damager"),
    PROCREASE("Procrease"),
    AMOCREASE("Ammo Increase"),
    SPEEDY("Speed Boost");

    private final String displayName;

    AbilitiName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
