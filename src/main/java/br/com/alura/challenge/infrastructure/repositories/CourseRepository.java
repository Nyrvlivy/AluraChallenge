package br.com.alura.challenge.infrastructure.repositories;

import br.com.alura.challenge.infrastructure.entities.CourseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<CourseEntity, Long> {
    Optional<CourseEntity> findByCode(String code);

}
