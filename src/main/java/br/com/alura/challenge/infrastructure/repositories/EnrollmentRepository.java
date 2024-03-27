package br.com.alura.challenge.infrastructure.repositories;

import br.com.alura.challenge.infrastructure.entities.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    Optional<EnrollmentEntity> findByUserIdAndCourseId(Long userId, Long courseId);

}
