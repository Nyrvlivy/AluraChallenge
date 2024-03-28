package br.com.alura.challenge.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsernameValidatorTest {

    @Test
    public void testUsernameValidatorWithValidUsernames() {
        assertTrue(UsernameValidator.isUsernameValid("validusername"));
        assertTrue(UsernameValidator.isUsernameValid("anothervalidusername"));
        assertTrue(UsernameValidator.isUsernameValid("brunamassi"));           // Hii! That's me! =)
    }

    @Test
    public void testUsernameValidatorWithInvalidUsernames() {
        assertFalse(UsernameValidator.isUsernameValid("InvalidUsername")); // Contains uppercase letters
        assertFalse(UsernameValidator.isUsernameValid("username123"));     // Contains numbers
        assertFalse(UsernameValidator.isUsernameValid("user name"));       // Contains space
        assertFalse(UsernameValidator.isUsernameValid("username!"));       // Contains special character
        assertFalse(UsernameValidator.isUsernameValid(""));                // Empty string
        assertFalse(UsernameValidator.isUsernameValid(null));              // Null value
        assertFalse(UsernameValidator.isUsernameValid("Game of Thrones")); // A good TV show, but not a valid username
    }
}
