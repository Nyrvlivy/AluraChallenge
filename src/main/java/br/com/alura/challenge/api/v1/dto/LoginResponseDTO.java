package br.com.alura.challenge.api.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private String token;
    private long expiresIn;

    public long setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return expiresIn;
    }

}