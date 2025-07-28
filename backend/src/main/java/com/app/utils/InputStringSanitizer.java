package com.app.utils;

import org.apache.commons.lang3.StringUtils;

public class InputStringSanitizer {
    /**
     * Escapes basic HTML characters to prevent XSS attacks.
     *
     * @param input The raw input string to sanitize.
     * @return The sanitized string safe for HTML rendering.
     */
    public static String sanitize(String input) {
        if (input == null) return StringUtils.EMPTY;

        return input
                .replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#x27;")
                .replaceAll("/", "&#x2F;");
    }
}
