package br.com.alura.challenge.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailValidatorTest {

    @Test
    public void testEmailValidatorWithValidEmails() {
        assertTrue(EmailValidator.isEmailValid("test@example.com"));
        assertTrue(EmailValidator.isEmailValid("firstname.lastname@domain.com"));
        assertTrue(EmailValidator.isEmailValid("email@subdomain.domain.com"));
        assertTrue(EmailValidator.isEmailValid("1234567890@domain.com"));
        assertTrue(EmailValidator.isEmailValid("email@domain-one.com"));
        assertTrue(EmailValidator.isEmailValid("_______@domain.com"));
        assertTrue(EmailValidator.isEmailValid("email@domain.co.jp"));
        assertTrue(EmailValidator.isEmailValid("firstname+lastname@domain.com"));
        assertTrue(EmailValidator.isEmailValid("AdventureTime@domain.com"));
    }

    @Test
    public void testEmailValidatorWithInvalidEmails() {
        assertFalse(EmailValidator.isEmailValid("simpsons"));                         // Missing @
        assertFalse(EmailValidator.isEmailValid("@no-local-part.com"));               // Empty local part
        assertFalse(EmailValidator.isEmailValid("email@domain@domain.com"));          // Multiple @
        assertFalse(EmailValidator.isEmailValid(".email@domain.com"));                // Leading dot
        assertFalse(EmailValidator.isEmailValid("email.@domain.com"));                // Trailing dot
        assertFalse(EmailValidator.isEmailValid("email..email@domain.com"));          // Multiple consecutive dots
        assertFalse(EmailValidator.isEmailValid("あいうえお@domain.com"));             // Unicode characters
        assertFalse(EmailValidator.isEmailValid("email@domain.com (Breaking Bad)")); // Special characters
        assertFalse(EmailValidator.isEmailValid("email@-domain.com"));               // Leading hyphen
        assertFalse(EmailValidator.isEmailValid("email@domain..com"));               // Trailing dot
    }

    @Test
    public void testEmailValidatorWithExceedingLength() {
        // Local part is longer than 64 characters
        assertFalse(EmailValidator.isEmailValid("a".repeat(65) + "@domain.com"));
        // Domain part is longer than 255 characters
        assertFalse(EmailValidator.isEmailValid("local@" + "d".repeat(256) + ".com"));
        // Total length is more than 320 characters
        assertFalse(EmailValidator.isEmailValid("local@" + "d".repeat(255) + ".com" + "a".repeat(65)));
    }
}
