package br.com.alura.challenge.utils;

public class UsernameValidator {

    private static final String USERNAME_PATTERN = "^[a-z]+$";

    public static boolean isUsernameValid(String username) {
        if (username == null) {
            return false;
        }
        return username.matches(USERNAME_PATTERN);
    }
}
