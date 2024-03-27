package br.com.alura.challenge.business.services;

import br.com.alura.challenge.api.v1.dto.UserDetailsDTO;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import br.com.alura.challenge.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> allUsers() {
        List<UserEntity> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDetailsDTO toUserDetailsDTO(UserEntity userEntity) {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setName(userEntity.getName());
        dto.setEmail(userEntity.getEmail());
        dto.setRole(userEntity.getRole().getName().name());
        return dto;
    }




}