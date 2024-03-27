package br.com.alura.challenge.business.services;

import br.com.alura.challenge.infrastructure.entities.CourseEntity;
import br.com.alura.challenge.infrastructure.entities.EnrollmentEntity;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import br.com.alura.challenge.infrastructure.repositories.CourseRepository;
import br.com.alura.challenge.infrastructure.repositories.EnrollmentRepository;
import br.com.alura.challenge.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public EnrollmentEntity enrollInCourse(Long userId, Long courseId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        CourseEntity course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.isStatus()) {
            throw new IllegalStateException("Course is not active");
        }

        Optional<EnrollmentEntity> existingEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
        if (existingEnrollment.isPresent()) {
            throw new IllegalStateException("User is already enrolled in this course");
        }

        EnrollmentEntity newEnrollment = new EnrollmentEntity();
        newEnrollment.setUser(user);
        newEnrollment.setCourse(course);

        return enrollmentRepository.save(newEnrollment);
    }
}
