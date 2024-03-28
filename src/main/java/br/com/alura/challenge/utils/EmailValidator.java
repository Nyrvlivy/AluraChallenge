package br.com.alura.challenge.utils;

public class EmailValidator {

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean isEmailValid(String email) {
        if (email == null || !email.matches(EMAIL_PATTERN)) {
            return false;
        }

        String[] parts = email.split("@", 2);
        if (parts.length != 2) {
            return false;
        }

        String localPart = parts[0];
        String domainPart = parts[1];

        return localPart.length() <= 64 && domainPart.length() <= 255 && email.length() <= 320;
    }
}
