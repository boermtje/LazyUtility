package net.botwithus.api.util;

public class StringFormatter {
    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        input = input.toLowerCase();
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }
}
