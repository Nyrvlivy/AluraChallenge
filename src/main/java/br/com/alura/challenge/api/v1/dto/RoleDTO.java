package br.com.alura.challenge.api.v1.dto;

import br.com.alura.challenge.infrastructure.entities.RoleEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {

    @JsonProperty(value = "id")
    private Integer id;
    @JsonProperty(value = "name")
    private String name;

    public RoleDTO(RoleEntity roleEntity) {
    }

}
