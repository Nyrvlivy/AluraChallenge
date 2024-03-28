package br.com.alura.challenge.business.services;

import br.com.alura.challenge.api.v1.dto.RatingDTO;
import br.com.alura.challenge.infrastructure.entities.RatingEntity;
import br.com.alura.challenge.infrastructure.repositories.CourseRepository;
import br.com.alura.challenge.infrastructure.repositories.RatingRepository;
import br.com.alura.challenge.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EmailSenderService emailSenderService;

    @Autowired
    public RatingService(RatingRepository ratingRepository, UserRepository userRepository, CourseRepository courseRepository, EmailSenderService emailSenderService) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.emailSenderService = emailSenderService;
    }

    @Transactional
    public void rateCourse(RatingDTO ratingDTO) {
        RatingEntity rating = new RatingEntity();
        rating.setUser(userRepository.findById(ratingDTO.getUserId()).orElseThrow());
        rating.setCourse(courseRepository.findById(ratingDTO.getCourseId()).orElseThrow());
        rating.setScore(ratingDTO.getScore());
        rating.setComment(ratingDTO.getComment());
        rating.setRatedAt(LocalDateTime.now());

        ratingRepository.save(rating);

        if (rating.getScore() < 6) {
            sendLowScoreNotification(rating);
        }
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
            // Log and handle the exception
            e.printStackTrace();
        }
    }
}
