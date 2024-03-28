package br.com.alura.challenge.api.v1.mapper;

import br.com.alura.challenge.api.v1.dto.response.RatingResponseDTO;
import br.com.alura.challenge.infrastructure.entities.RatingEntity;

public interface RatingMapper {
    RatingResponseDTO toRatingResponseDTO(RatingEntity ratingEntity);
}
