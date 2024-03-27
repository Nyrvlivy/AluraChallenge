package br.com.alura.challenge.api.v1.mapper.impl;

import br.com.alura.challenge.api.v1.dto.response.EnrollmentResponseDTO;
import br.com.alura.challenge.api.v1.mapper.EnrollmentMapper;
import br.com.alura.challenge.infrastructure.entities.EnrollmentEntity;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapperImpl implements EnrollmentMapper {

    @Override
    public EnrollmentResponseDTO toDTO(EnrollmentEntity enrollment) {
        if (enrollment == null) {
            return null;
        }

        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
        dto.setId(enrollment.getId());
        if (enrollment.getUser() != null) {
            dto.setUserId(enrollment.getUser().getId());
        }
        if (enrollment.getCourse() != null) {
            dto.setCourseId(enrollment.getCourse().getId());
        }
        dto.setEnrolledAt(enrollment.getEnrolledAt());
        return dto;
    }
}
