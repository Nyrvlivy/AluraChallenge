package br.com.alura.challenge.api.v1.mapper;

import br.com.alura.challenge.api.v1.dto.response.EnrollmentResponseDTO;
import br.com.alura.challenge.infrastructure.entities.EnrollmentEntity;

public interface EnrollmentMapper {

    EnrollmentResponseDTO toDTO(EnrollmentEntity enrollment);

}
