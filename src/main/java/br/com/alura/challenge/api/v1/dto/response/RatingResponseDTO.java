package br.com.alura.challenge.api.v1.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RatingResponseDTO {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("course_id")
    private Long courseId;
    @JsonProperty("score")
    private Integer score;
    @JsonProperty("comment")
    private String comment;

    @JsonProperty("rated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ratedAt;
}

