package br.com.alura.challenge.api.v1.controller;

import br.com.alura.challenge.api.v1.dto.request.EnrollmentRequestDTO;
import br.com.alura.challenge.api.v1.dto.response.EnrollmentResponseDTO;
import br.com.alura.challenge.api.v1.mapper.EnrollmentMapper;
import br.com.alura.challenge.business.services.EnrollmentService;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollments", description = "Operations related to course enrollments.")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final EnrollmentMapper enrollmentMapper;

    @Operation(summary = "Enroll in Course", description = "Enrolls the authenticated user in a course. User privileges required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enrollment successful"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @CrossOrigin
    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> enroll(@RequestBody EnrollmentRequestDTO enrollmentDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((UserEntity) authentication.getPrincipal()).getId();

        var enrollment = enrollmentService.enrollInCourse(userId, enrollmentDTO.getCourseId());
        var enrollmentResponseDTO = enrollmentMapper.toDTO(enrollment);

        return ResponseEntity.ok(enrollmentResponseDTO);
    }
}
