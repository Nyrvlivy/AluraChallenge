package br.com.alura.challenge.api.v1.controller;

import br.com.alura.challenge.api.v1.dto.RatingDTO;
import br.com.alura.challenge.business.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<?> rateCourse(@RequestBody RatingDTO ratingDTO) {
        ratingService.rateCourse(ratingDTO);
        return ResponseEntity.ok("Evaluation received successfully!");
    }
}
