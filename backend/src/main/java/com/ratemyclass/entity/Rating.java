package com.ratemyclass.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ratings", indexes = {
    @Index(name = "idx_rating_user", columnList = "user_id"),
    @Index(name = "idx_rating_professor", columnList = "professor_id"),
    @Index(name = "idx_rating_course", columnList = "course_id"),
    @Index(name = "idx_rating_created", columnList = "created_at")
}, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "professor_id", "course_id"})
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "1.0", message = "Overall rating must be at least 1.0")
    @DecimalMax(value = "5.0", message = "Overall rating must be at most 5.0")
    @Column(nullable = false)
    private Double overallRating;

    @DecimalMin(value = "1.0")
    @DecimalMax(value = "5.0")
    @Column
    private Double teachingQuality;

    @DecimalMin(value = "1.0")
    @DecimalMax(value = "5.0")
    @Column
    private Double difficulty;

    @DecimalMin(value = "1.0")
    @DecimalMax(value = "5.0")
    @Column
    private Double workload;

    @DecimalMin(value = "1.0")
    @DecimalMax(value = "5.0")
    @Column
    private Double clarity;

    @Size(max = 2000, message = "Review text cannot exceed 2000 characters")
    @Column(length = 2000)
    private String reviewText;

    @Column(nullable = false)
    private Boolean wouldTakeAgain = false;

    @Column(nullable = false)
    private Boolean isAnonymous = false;

    @Size(max = 20)
    private String semester;

    @Min(value = 2000)
    @Max(value = 2030)
    private Integer year;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Rating() {}

    public Rating(User user, Professor professor, Course course, Double overallRating) {
        this.user = user;
        this.professor = professor;
        this.course = course;
        this.overallRating = overallRating;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getOverallRating() { return overallRating; }
    public void setOverallRating(Double overallRating) { this.overallRating = overallRating; }

    public Double getTeachingQuality() { return teachingQuality; }
    public void setTeachingQuality(Double teachingQuality) { this.teachingQuality = teachingQuality; }

    public Double getDifficulty() { return difficulty; }
    public void setDifficulty(Double difficulty) { this.difficulty = difficulty; }

    public Double getWorkload() { return workload; }
    public void setWorkload(Double workload) { this.workload = workload; }

    public Double getClarity() { return clarity; }
    public void setClarity(Double clarity) { this.clarity = clarity; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public Boolean getWouldTakeAgain() { return wouldTakeAgain; }
    public void setWouldTakeAgain(Boolean wouldTakeAgain) { this.wouldTakeAgain = wouldTakeAgain; }

    public Boolean getIsAnonymous() { return isAnonymous; }
    public void setIsAnonymous(Boolean isAnonymous) { this.isAnonymous = isAnonymous; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(id, rating.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", overallRating=" + overallRating +
                ", user=" + (user != null ? user.getEmail() : null) +
                ", professor=" + (professor != null ? professor.getFullName() : null) +
                ", course=" + (course != null ? course.getCode() : null) +
                '}';
    }
}