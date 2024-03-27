package br.com.alura.challenge.api.v1.mapper;

import br.com.alura.challenge.api.v1.dto.response.CourseResponseDTO;
import br.com.alura.challenge.infrastructure.entities.CourseEntity;

public interface CourseMapper {

    CourseResponseDTO toCourseResponseDTO(CourseEntity courseEntity);

}
