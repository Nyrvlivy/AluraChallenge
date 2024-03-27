package br.com.alura.challenge.api.v1.controller;

import br.com.alura.challenge.api.v1.dto.CourseDTO;
import br.com.alura.challenge.api.v1.dto.CourseResponseDTO;
import br.com.alura.challenge.business.services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
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
}
