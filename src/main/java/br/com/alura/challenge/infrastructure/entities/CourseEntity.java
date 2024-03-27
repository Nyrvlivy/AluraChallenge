package br.com.alura.challenge.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "CourseEntity")
@Table(name = "courses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"})
})
@Getter
@Setter
@NoArgsConstructor
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", nullable = false)
    private UserEntity instructor;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private boolean status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "inactive_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inactiveAt;

}
