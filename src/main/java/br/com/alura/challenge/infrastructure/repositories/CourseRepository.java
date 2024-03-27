package br.com.alura.challenge.infrastructure.repositories;

import br.com.alura.challenge.infrastructure.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    Optional<CourseEntity> findByCode(String code);

    Page<CourseEntity> findByStatus(boolean status, Pageable pageable);

}
