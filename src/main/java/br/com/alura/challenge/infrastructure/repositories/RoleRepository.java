package br.com.alura.challenge.infrastructure.repositories;

import br.com.alura.challenge.infrastructure.entities.RoleEntity;
import br.com.alura.challenge.infrastructure.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(RoleName name);
}
