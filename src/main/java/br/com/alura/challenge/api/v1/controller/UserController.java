package br.com.alura.challenge.api.v1.controller;


import br.com.alura.challenge.api.v1.dto.UserDetailsDTO;
import br.com.alura.challenge.business.services.UserService;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operations related to users.")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Retrieve Authenticated User", description = "Fetches details of the currently authenticated user. User privileges required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authenticated user details successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Requires authentication"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/me")
    public ResponseEntity<UserEntity> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @Operation(summary = "List All Users", description = "Retrieves a list of all users in the system. Admin privileges required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Requires authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires admin privileges"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserEntity>> allUsers() {
        List<UserEntity> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Retrieve User by Username", description = "Fetches details of a user by their username. Admin privileges required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details by username successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Requires authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires admin privileges"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @CrossOrigin
    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDetailsDTO> getUserByUsername(@PathVariable String username) {
        UserEntity user = userService.findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        UserDetailsDTO userResponse = userService.toUserDetailsDTO(user);
        return ResponseEntity.ok(userResponse);
    }
}
