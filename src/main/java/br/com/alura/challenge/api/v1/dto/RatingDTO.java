// Rating DTO
package br.com.alura.challenge.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {

    @JsonProperty(value = "course_id")
    private Long courseId;
    @JsonProperty(value = "score")
    private Integer score;
    @JsonProperty(value = "comment")
    private String comment;

}
