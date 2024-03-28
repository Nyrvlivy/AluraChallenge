package br.com.alura.challenge.api.v1.controller;

import br.com.alura.challenge.api.v1.dto.LoginUserDTO;
import br.com.alura.challenge.api.v1.dto.RegisterUserDTO;
import br.com.alura.challenge.api.v1.dto.response.LoginResponseDTO;
import br.com.alura.challenge.business.services.AuthenticationService;
import br.com.alura.challenge.business.services.JwtService;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Operations related to user authentication.")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register User", description = "Registers a new user to the system. No authentication required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @CrossOrigin
    @PostMapping("/signup")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterUserDTO RegisterUserDTO) {
        UserEntity registeredUser = authenticationService.signup(RegisterUserDTO);

        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "Authenticate User", description = "Authenticates a user by validating login credentials. No authentication required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully authenticated"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @CrossOrigin
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
