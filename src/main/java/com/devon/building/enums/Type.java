package com.devon.building.enums;

import java.util.Locale;
import java.util.Optional;

public enum Type {
    TANG_TRET("Tầng trệt"),
    NGUYEN_CAN("Nguyên căn"),
    NOI_THAT("Nội thất");

    private final String label;

    Type(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Optional<Type> parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return Optional.empty();
        }
        String code = raw.trim().toUpperCase(Locale.ROOT).replace(' ', '_');
        try {
            return Optional.of(Type.valueOf(code));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}
