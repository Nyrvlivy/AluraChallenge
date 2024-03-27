package br.com.alura.challenge.api.v1.controller;

import br.com.alura.challenge.api.v1.dto.LoginResponseDTO;
import br.com.alura.challenge.api.v1.dto.LoginUserDTO;
import br.com.alura.challenge.api.v1.dto.RegisterUserDTO;
import br.com.alura.challenge.business.services.AuthenticationService;
import br.com.alura.challenge.business.services.JwtService;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterUserDTO RegisterUserDTO) {
        UserEntity registeredUser = authenticationService.signup(RegisterUserDTO);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginUserDTO LoginUserDTO) {
        UserEntity authenticatedUser = authenticationService.authenticate(LoginUserDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}