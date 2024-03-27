package br.com.alura.challenge.api.v1.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentRequestDTO {

    @JsonProperty(value = "course_id")
    private Long courseId;

}
