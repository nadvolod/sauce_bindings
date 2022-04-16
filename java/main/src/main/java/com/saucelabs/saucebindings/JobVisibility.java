package com.saucelabs.saucebindings;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum JobVisibility {
    PUBLIC("public"),
    PUBLIC_RESTRICTED("public restricted"),
    SHARE("share"),
    TEAM("team"),
    PRIVATE("private");

    @Getter
    private final String value;

    private static final class JobVisibilityLookup {
        private static final Map<String, String> lookup = new HashMap<>();
    }

    public static Set<String> keys() {
        return JobVisibilityLookup.lookup.keySet();
    }

    JobVisibility(String value) {
        this.value = value;
        JobVisibilityLookup.lookup.put(value, this.name());
    }

    public static String fromString(String value) {
        return JobVisibilityLookup.lookup.get(value);
    }

    public String toString() {
        return this.value;
    }
}
