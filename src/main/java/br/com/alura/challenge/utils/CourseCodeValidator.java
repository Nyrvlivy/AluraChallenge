package br.com.alura.challenge.utils;

public class CourseCodeValidator {
    private static final String COURSE_CODE_PATTERN = "^[a-zA-Z-]{1,10}$";

    public static boolean isCourseCodeValid(String courseCode) {
        if (courseCode == null) {
            return false;
        }
        return courseCode.matches(COURSE_CODE_PATTERN);
    }
}
