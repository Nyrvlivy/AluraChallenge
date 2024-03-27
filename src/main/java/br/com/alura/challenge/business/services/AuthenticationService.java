package br.com.alura.challenge.business.services;

import br.com.alura.challenge.api.v1.dto.LoginUserDTO;
import br.com.alura.challenge.api.v1.dto.RegisterUserDTO;
import br.com.alura.challenge.infrastructure.entities.RoleEntity;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import br.com.alura.challenge.infrastructure.enums.RoleName;
import br.com.alura.challenge.infrastructure.repositories.RoleRepository;
import br.com.alura.challenge.infrastructure.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity signup(RegisterUserDTO input) {
        UserEntity newUser = new UserEntity();
        newUser.setName(input.getName());
        newUser.setUsername(input.getUsername());
        newUser.setEmail(input.getEmail());
        newUser.setPassword(passwordEncoder.encode(input.getPassword()));

        RoleEntity assignedRole;
        if (input.getRole() != null) {
            assignedRole = roleRepository.findByName(RoleName.valueOf(input.getRole().getName()))
                    .orElseThrow(() -> new RuntimeException("Role not found"));
        } else {
            assignedRole = roleRepository.findByName(RoleName.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
        }
        newUser.setRole(assignedRole);

        return userRepository.save(newUser);
    }

    public UserEntity authenticate(LoginUserDTO input) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getLogin(),
                        input.getPassword()
                )
        );

        String usernameOrEmail = input.getLogin();
        Optional<UserEntity> userEntity = userRepository.findByEmail(usernameOrEmail);
        if (userEntity.isEmpty()) {
            userEntity = userRepository.findByUsername(usernameOrEmail);
        }

        return userEntity.orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));
    }

}