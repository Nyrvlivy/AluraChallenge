package br.com.alura.challenge.api.v1.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EnrollmentResponseDTO {

    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "user_id")
    private Long userId;
    @JsonProperty(value = "course_id")
    private Long courseId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "enrolled_at")
    private LocalDateTime enrolledAt;

}
