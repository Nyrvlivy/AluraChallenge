package br.com.alura.challenge.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseCodeValidatorTest {

    @Test
    public void testCourseCodeValidatorWithValidCodes() {
        assertTrue(CourseCodeValidator.isCourseCodeValid("java"));
        assertTrue(CourseCodeValidator.isCourseCodeValid("Spring-Boot"));
        assertTrue(CourseCodeValidator.isCourseCodeValid("Java"));
        assertTrue(CourseCodeValidator.isCourseCodeValid("Html-Css"));
        assertTrue(CourseCodeValidator.isCourseCodeValid("React-Js"));
    }

    @Test
    public void testCourseCodeValidatorWithInvalidCodes() {
        assertFalse(CourseCodeValidator.isCourseCodeValid("12345"));                 // Contains numerics
        assertFalse(CourseCodeValidator.isCourseCodeValid("spring boot"));           // Contains space
        assertFalse(CourseCodeValidator.isCourseCodeValid("java_script"));           // Contains underscore
        assertFalse(CourseCodeValidator.isCourseCodeValid(""));                      // Empty string
        assertFalse(CourseCodeValidator.isCourseCodeValid(null));                    // Null
        assertFalse(CourseCodeValidator.isCourseCodeValid("very-long-course-name")); // Exceeds 10 characters
    }
}
