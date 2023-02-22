package io.stardate.extensions;

public class Parse {
    public static java.util.Optional<Integer> tryParseInt(String str) {
        try {
            return java.util.Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return java.util.Optional.empty();
        }
    }
}
