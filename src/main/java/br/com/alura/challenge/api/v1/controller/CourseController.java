package br.com.alura.challenge.api.v1.controller;

import br.com.alura.challenge.api.v1.dto.CourseDTO;
import br.com.alura.challenge.api.v1.dto.response.CourseResponseDTO;
import br.com.alura.challenge.api.v1.mapper.CourseMapper;
import br.com.alura.challenge.business.services.CourseService;
import br.com.alura.challenge.infrastructure.entities.CourseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Courses", description = "Operations related to courses.")
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @Operation(summary = "Create Course", description = "Creates a new course, assigning an instructor. Admin privileges required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Requires authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires admin privileges"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.createCourse(courseDTO));
    }

    @Operation(summary = "Disable Course", description = "Disables a course without deletion. Admin privileges required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course successfully disabled"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Requires authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires admin privileges"),
            @ApiResponse(responseCode = "404", description = "Course not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{code}/disable")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CourseResponseDTO> disableCourse(@PathVariable String code) {
        CourseResponseDTO courseResponseDTO = courseService.disableCourse(code);
        return ResponseEntity.ok(courseResponseDTO);
    }

    @Operation(summary = "Enable Course", description = "Enables a previously disabled course. Admin privileges required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course successfully enabled"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Requires authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires admin privileges"),
            @ApiResponse(responseCode = "404", description = "Course not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{code}/enable")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CourseResponseDTO> enableCourse(@PathVariable String code) {
        CourseResponseDTO courseResponseDTO = courseService.enableCourse(code);
        return ResponseEntity.ok(courseResponseDTO);
    }

    @Operation(summary = "List Courses", description = "Lists all courses. Supports pagination and optional status filtering. Admin privileges required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courses listed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Requires authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires admin privileges"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<CourseResponseDTO>> listCourses(
            @Parameter(description = "Page number (starts from 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Size of the page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filters courses by status (true for active, false for inactive)", example = "true", required = false)
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
