package br.com.alura.challenge.api.v1.mapper.impl;

import br.com.alura.challenge.api.v1.dto.response.CourseResponseDTO;
import br.com.alura.challenge.api.v1.mapper.CourseMapper;
import br.com.alura.challenge.infrastructure.entities.CourseEntity;
import org.springframework.stereotype.Component;

@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseResponseDTO toCourseResponseDTO(CourseEntity courseEntity) {
        if (courseEntity == null) {
            return null;
        }

        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setName(courseEntity.getName());
        dto.setCode(courseEntity.getCode());
        dto.setInstructorName(courseEntity.getInstructor().getName());
        dto.setDescription(courseEntity.getDescription());
        dto.setStatus(courseEntity.isStatus());
        dto.setCreatedAt(courseEntity.getCreatedAt());
        dto.setInactiveAt(courseEntity.getInactiveAt());
        return dto;
    }
}
