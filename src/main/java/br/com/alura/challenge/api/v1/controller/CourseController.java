package br.com.alura.challenge.api.v1.controller;

import br.com.alura.challenge.api.v1.dto.CourseDTO;
import br.com.alura.challenge.api.v1.dto.CourseResponseDTO;
import br.com.alura.challenge.api.v1.mapper.CourseMapper;
import br.com.alura.challenge.business.services.CourseService;
import br.com.alura.challenge.infrastructure.entities.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.createCourse(courseDTO));
    }

    @PutMapping("/{code}/disable")
    public ResponseEntity<CourseResponseDTO> disableCourse(@PathVariable String code) {
        CourseResponseDTO courseResponseDTO = courseService.disableCourse(code);
        return ResponseEntity.ok(courseResponseDTO);
    }

    @PutMapping("/{code}/enable")
    public ResponseEntity<CourseResponseDTO> enableCourse(@PathVariable String code) {
        CourseResponseDTO courseResponseDTO = courseService.enableCourse(code);
        return ResponseEntity.ok(courseResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponseDTO>> listCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean status) {

        Page<CourseEntity> courses;
        Pageable pageable = PageRequest.of(page, size);

        if (status == null) {
            courses = courseService.findAll(pageable);
        } else {
            courses = courseService.findByStatus(status, pageable);
        }

        Page<CourseResponseDTO> courseDTOs = courses.map(courseMapper::toCourseResponseDTO);

        return ResponseEntity.ok(courseDTOs);
    }
}
