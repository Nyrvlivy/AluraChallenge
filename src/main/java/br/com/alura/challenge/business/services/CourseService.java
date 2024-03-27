package br.com.alura.challenge.business.services;

import br.com.alura.challenge.api.v1.dto.CourseDTO;
import br.com.alura.challenge.api.v1.dto.CourseResponseDTO;
import br.com.alura.challenge.infrastructure.entities.CourseEntity;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import br.com.alura.challenge.infrastructure.repositories.CourseRepository;
import br.com.alura.challenge.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public CourseResponseDTO createCourse(CourseDTO courseDTO) {

        boolean status = courseDTO.getStatus() == null || courseDTO.getStatus();

        if (courseDTO.getInstructorId() == null) {
            throw new IllegalArgumentException("Instructor ID cannot be null");
        }

        Integer instructorId = courseDTO.getInstructorId().intValue();
        UserEntity instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        if (!instructor.getRole().getName().name().equals("ROLE_INSTRUCTOR")) {
            throw new IllegalArgumentException("Only instructors can create courses");
        }

        CourseEntity course = new CourseEntity();
        course.setName(courseDTO.getName());
        course.setCode(courseDTO.getCode());
        course.setInstructor(instructor);
        course.setDescription(courseDTO.getDescription());
        course.setStatus(status);

        course = courseRepository.save(course);

        CourseResponseDTO responseDTO = new CourseResponseDTO();
        responseDTO.setName(course.getName());
        responseDTO.setCode(course.getCode());
        responseDTO.setInstructorName(course.getInstructor().getName());
        responseDTO.setDescription(course.getDescription());
        responseDTO.setStatus(course.isStatus());
        responseDTO.setCreatedAt(course.getCreatedAt());

        if (!course.isStatus()) {
            responseDTO.setInactiveAt(course.getInactiveAt());
        }

        return responseDTO;
    }

    public CourseResponseDTO disableCourse(String code) {
        CourseEntity course = courseRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (!course.isStatus()) {
            throw new IllegalStateException("Course is already inactive");
        }

        course.setStatus(false);
        course.setInactiveAt(LocalDateTime.now());

        course = courseRepository.save(course);

        return convertToCourseResponseDTO(course);
    }

    private CourseResponseDTO convertToCourseResponseDTO(CourseEntity course) {
        CourseResponseDTO responseDTO = new CourseResponseDTO();
        responseDTO.setName(course.getName());
        responseDTO.setCode(course.getCode());
        responseDTO.setInstructorName(course.getInstructor().getName());
        responseDTO.setDescription(course.getDescription());
        responseDTO.setStatus(course.isStatus());
        responseDTO.setCreatedAt(course.getCreatedAt());
        responseDTO.setInactiveAt(course.getInactiveAt());
        return responseDTO;
    }

    public CourseResponseDTO enableCourse(String code) {
        CourseEntity course = courseRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.isStatus()) {
            throw new IllegalStateException("Course is already active");
        }

        course.setStatus(true);
        course.setInactiveAt(null);

        course = courseRepository.save(course);

        return convertToCourseResponseDTO(course);
    }


}
