package br.com.alura.challenge.business.services;

import br.com.alura.challenge.api.v1.dto.LoginUserDTO;
import br.com.alura.challenge.api.v1.dto.RegisterUserDTO;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import br.com.alura.challenge.infrastructure.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity signup(RegisterUserDTO input) {
        UserEntity newUser = new UserEntity();
        newUser.setName(input.getName());
        newUser.setUsername(input.getUsername());
        newUser.setEmail(input.getEmail());
        newUser.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(newUser);
    }

    public UserEntity authenticate(LoginUserDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}