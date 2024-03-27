package br.com.alura.challenge.api.v1.controller;

import br.com.alura.challenge.api.v1.dto.request.EnrollmentRequestDTO;
import br.com.alura.challenge.api.v1.dto.response.EnrollmentResponseDTO;
import br.com.alura.challenge.api.v1.mapper.EnrollmentMapper;
import br.com.alura.challenge.business.services.EnrollmentService;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentController(EnrollmentService enrollmentService, EnrollmentMapper enrollmentMapper) {
        this.enrollmentService = enrollmentService;
        this.enrollmentMapper = enrollmentMapper;
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> enroll(@RequestBody EnrollmentRequestDTO enrollmentDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((UserEntity) authentication.getPrincipal()).getId();

        var enrollment = enrollmentService.enrollInCourse(userId, enrollmentDTO.getCourseId());
        var enrollmentResponseDTO = enrollmentMapper.toDTO(enrollment);

        return ResponseEntity.ok(enrollmentResponseDTO);
    }
}
