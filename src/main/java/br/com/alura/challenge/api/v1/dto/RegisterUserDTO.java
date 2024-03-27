package br.com.alura.challenge.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {

    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "username")
    private String username;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "role")
    private RoleDTO role;

}