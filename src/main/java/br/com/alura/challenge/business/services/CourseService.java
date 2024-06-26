package br.com.alura.challenge.business.services;

import br.com.alura.challenge.api.v1.dto.CourseDTO;
import br.com.alura.challenge.api.v1.dto.response.CourseResponseDTO;
import br.com.alura.challenge.api.v1.mapper.CourseMapper;
import br.com.alura.challenge.infrastructure.entities.CourseEntity;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import br.com.alura.challenge.infrastructure.repositories.CourseRepository;
import br.com.alura.challenge.infrastructure.repositories.UserRepository;
import br.com.alura.challenge.utils.CourseCodeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseMapper = courseMapper;
    }

    @Transactional
    public CourseResponseDTO createCourse(CourseDTO courseDTO) {
        if (!CourseCodeValidator.isCourseCodeValid(courseDTO.getCode())) {
            throw new IllegalArgumentException("Invalid course code");
        }

        boolean status = courseDTO.getStatus() == null || courseDTO.getStatus();

        if (courseDTO.getInstructorId() == null) {
            throw new IllegalArgumentException("Instructor ID cannot be null");
        }

        Long instructorId = courseDTO.getInstructorId();
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

        return courseMapper.toCourseResponseDTO(course);
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

        return courseMapper.toCourseResponseDTO(course);
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

        return courseMapper.toCourseResponseDTO(course);
    }

    public Page<CourseEntity> findAll(Pageable pageable) {
        Pageable sortedByStatusPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Order.desc("status"), Sort.Order.asc("name")));

        return courseRepository.findAll(sortedByStatusPageable);
    }

    public Page<CourseEntity> findByStatus(boolean status, Pageable pageable) {
        return courseRepository.findByStatus(status, pageable);
    }
}
