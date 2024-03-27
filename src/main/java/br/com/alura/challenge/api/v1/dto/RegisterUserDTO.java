package br.com.alura.challenge.api.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {
    private String name;
    private String username;
    private String email;
    private String password;

}