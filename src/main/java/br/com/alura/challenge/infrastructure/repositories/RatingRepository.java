package br.com.alura.challenge.infrastructure.repositories;

import br.com.alura.challenge.infrastructure.entities.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    List<RatingEntity> findByCourseId(Long courseId);

    List<RatingEntity> findByCourseIdAndScoreLessThan(Long courseId, Integer score);

}
