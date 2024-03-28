package br.com.alura.challenge.api.v1.mapper.impl;

import br.com.alura.challenge.api.v1.dto.response.RatingResponseDTO;
import br.com.alura.challenge.api.v1.mapper.RatingMapper;
import br.com.alura.challenge.infrastructure.entities.RatingEntity;
import org.springframework.stereotype.Component;

@Component
public class RatingMapperImpl implements RatingMapper {

    @Override
    public RatingResponseDTO toRatingResponseDTO(RatingEntity ratingEntity) {
        if (ratingEntity == null) {
            return null;
        }

        RatingResponseDTO dto = new RatingResponseDTO();
        dto.setId(ratingEntity.getId());
        dto.setUserId(ratingEntity.getUser().getId());
        dto.setCourseId(ratingEntity.getCourse().getId());
        dto.setScore(ratingEntity.getScore());
        dto.setComment(ratingEntity.getComment());
        dto.setRatedAt(ratingEntity.getRatedAt());
        return dto;
    }
}
