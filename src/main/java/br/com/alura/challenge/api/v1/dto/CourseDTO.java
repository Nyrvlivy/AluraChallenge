package br.com.alura.challenge.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDTO {

    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "code")
    private String code;
    @JsonProperty(value = "instructor_id")
    private Long instructorId;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "status")
    private Boolean status;

}
