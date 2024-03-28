package br.com.alura.challenge.api.v1.controller;

import br.com.alura.challenge.api.v1.dto.RatingDTO;
import br.com.alura.challenge.api.v1.dto.response.RatingResponseDTO;
import br.com.alura.challenge.business.services.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
@Tag(name = "Ratings", description = "Operations related to course ratings.")
public class RatingController {

    private final RatingService ratingService;

    @Operation(summary = "Submit a Course Rating",
            description = "Submits a rating for a course, including a numerical score and an optional comment. User privileges required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating submitted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RatingResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Requires authentication"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @CrossOrigin
    @PostMapping
    public ResponseEntity<RatingResponseDTO> rateCourse(@RequestBody RatingDTO ratingDTO) {
        RatingResponseDTO createdRating = ratingService.rateCourse(ratingDTO);
        return ResponseEntity.ok(createdRating);
    }
}
