package com.tpty.tableegi.jamath.utils;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationChannel {
    EMAIL,
    SMS,
    WHATSAPP;

    /**
     * Return a friendly name (optional).
     */
    public String displayName() {
        switch (this) {
            case EMAIL:
                return "Email";
            case SMS:
                return "SMS";
            case WHATSAPP:
                return "WhatsApp";
            default:
                return this.name();
        }
    }

    /**
     * Jackson-friendly serialization: writes the enum name (uppercase).
     */
    @JsonValue
    public String toValue() {
        return this.name();
    }

    /**
     * Jackson-friendly deserialization: accepts case-insensitive names.
     */
    @JsonCreator
    public static NotificationChannel fromValue(String value) {
        if (value == null) return null;
        try {
            return NotificationChannel.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown NotificationChannel: " + value);
        }
    }
}

