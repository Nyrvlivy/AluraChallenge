package br.com.alura.challenge.business.services;

import br.com.alura.challenge.api.v1.dto.RatingDTO;
import br.com.alura.challenge.api.v1.dto.response.RatingResponseDTO;
import br.com.alura.challenge.api.v1.mapper.RatingMapper;
import br.com.alura.challenge.infrastructure.entities.CourseEntity;
import br.com.alura.challenge.infrastructure.entities.RatingEntity;
import br.com.alura.challenge.infrastructure.entities.UserEntity;
import br.com.alura.challenge.infrastructure.repositories.CourseRepository;
import br.com.alura.challenge.infrastructure.repositories.RatingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final CourseRepository courseRepository;
    private final EmailSenderService emailSenderService;
    private final RatingMapper ratingMapper;

    public RatingService(RatingRepository ratingRepository, CourseRepository courseRepository, EmailSenderService emailSenderService, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.courseRepository = courseRepository;
        this.emailSenderService = emailSenderService;
        this.ratingMapper = ratingMapper;
    }

    @Transactional
    public RatingResponseDTO rateCourse(RatingDTO ratingDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();

        CourseEntity course = courseRepository.findById(ratingDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        RatingEntity rating = new RatingEntity();
        rating.setUser(user);
        rating.setCourse(course);
        rating.setScore(ratingDTO.getScore());
        rating.setComment(ratingDTO.getComment());
        rating.setRatedAt(LocalDateTime.now());
        RatingEntity savedRating = ratingRepository.save(rating);

        if (savedRating.getScore() < 6) {
            sendLowScoreNotification(savedRating);
        }

        return ratingMapper.toRatingResponseDTO(savedRating);
    }

    private void sendLowScoreNotification(RatingEntity rating) {
        String instructorEmail = rating.getCourse().getInstructor().getEmail();
        Context context = new Context();
        context.setVariable("courseName", rating.getCourse().getName());
        context.setVariable("score", rating.getScore());
        context.setVariable("comment", rating.getComment());

        try {
            emailSenderService.sendEmail(instructorEmail, "Low Course Rating Alert", "emailTemplate.html", context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
