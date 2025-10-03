package com.ratemyclass.dto.professor;

import com.ratemyclass.entity.Professor;

import java.time.LocalDateTime;

public class ProfessorResponseDTO {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
    private String bio;
    private String photoUrl;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Department info
    private Long departmentId;
    private String departmentCode;
    private String departmentName;
    
    // Aggregated data
    private Long courseCount;
    private Long ratingCount;
    private Double averageRating;
    
    // Constructors
    public ProfessorResponseDTO() {}
    
    public ProfessorResponseDTO(Professor professor) {
        this.id = professor.getId();
        this.firstName = professor.getFirstName();
        this.lastName = professor.getLastName();
        this.title = professor.getTitle();
        this.bio = professor.getBio();
        this.photoUrl = professor.getPhotoUrl();
        this.active = professor.getActive();
        this.createdAt = professor.getCreatedAt();
        this.updatedAt = professor.getUpdatedAt();
        
        if (professor.getDepartment() != null) {
            this.departmentId = professor.getDepartment().getId();
            this.departmentCode = professor.getDepartment().getCode();
            this.departmentName = professor.getDepartment().getName();
        }
        
        if (professor.getCourses() != null) {
            this.courseCount = (long) professor.getCourses().size();
        }
        
        if (professor.getRatings() != null) {
            this.ratingCount = (long) professor.getRatings().size();
            this.averageRating = professor.getRatings().stream()
                    .mapToDouble(rating -> rating.getOverallRating())
                    .average()
                    .orElse(0.0);
        }
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    
    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }
    
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    
    public Long getCourseCount() { return courseCount; }
    public void setCourseCount(Long courseCount) { this.courseCount = courseCount; }
    
    public Long getRatingCount() { return ratingCount; }
    public void setRatingCount(Long ratingCount) { this.ratingCount = ratingCount; }
    
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    
    // Helper methods
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getDisplayName() {
        if (title != null && !title.isEmpty()) {
            return title + " " + getFullName();
        }
        return getFullName();
    }
}