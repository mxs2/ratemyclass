package com.ratemyclass.repository;

import com.ratemyclass.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r WHERE r.professor.id = :professorId AND r.active = true ORDER BY r.createdAt DESC")
    Page<Rating> findByProfessorId(@Param("professorId") Long professorId, Pageable pageable);

    @Query("SELECT r FROM Rating r WHERE r.course.id = :courseId AND r.active = true ORDER BY r.createdAt DESC")
    Page<Rating> findByCourseId(@Param("courseId") Long courseId, Pageable pageable);

    @Query("SELECT r FROM Rating r WHERE r.user.id = :userId AND r.active = true ORDER BY r.createdAt DESC")
    Page<Rating> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Rating r WHERE r.professor.id = :professorId AND r.course.id = :courseId AND r.active = true")
    Page<Rating> findByProfessorAndCourse(@Param("professorId") Long professorId, 
                                         @Param("courseId") Long courseId, 
                                         Pageable pageable);

    Optional<Rating> findByUserIdAndProfessorIdAndCourseId(Long userId, Long professorId, Long courseId);

    boolean existsByUserIdAndProfessorIdAndCourseId(Long userId, Long professorId, Long courseId);

    // Analytics queries
    @Query("SELECT AVG(r.overallRating) FROM Rating r WHERE r.professor.id = :professorId AND r.active = true")
    Double getAverageRatingForProfessor(@Param("professorId") Long professorId);

    @Query("SELECT AVG(r.overallRating) FROM Rating r WHERE r.course.id = :courseId AND r.active = true")
    Double getAverageRatingForCourse(@Param("courseId") Long courseId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.professor.id = :professorId AND r.active = true")
    Long countRatingsByProfessor(@Param("professorId") Long professorId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.course.id = :courseId AND r.active = true")
    Long countRatingsByCourse(@Param("courseId") Long courseId);

    @Query("SELECT AVG(r.teachingQuality) FROM Rating r WHERE r.professor.id = :professorId AND r.active = true")
    Double getAverageTeachingQualityForProfessor(@Param("professorId") Long professorId);

    @Query("SELECT AVG(r.difficulty) FROM Rating r WHERE r.professor.id = :professorId AND r.active = true")
    Double getAverageDifficultyForProfessor(@Param("professorId") Long professorId);

    @Query("SELECT AVG(r.workload) FROM Rating r WHERE r.professor.id = :professorId AND r.active = true")
    Double getAverageWorkloadForProfessor(@Param("professorId") Long professorId);

    @Query("SELECT AVG(r.clarity) FROM Rating r WHERE r.professor.id = :professorId AND r.active = true")
    Double getAverageClarityForProfessor(@Param("professorId") Long professorId);

    @Query("SELECT (COUNT(r) * 100.0 / (SELECT COUNT(r2) FROM Rating r2 WHERE r2.professor.id = :professorId AND r2.active = true)) " +
           "FROM Rating r WHERE r.professor.id = :professorId AND r.wouldTakeAgain = true AND r.active = true")
    Double getWouldTakeAgainPercentageForProfessor(@Param("professorId") Long professorId);

    @Query("SELECT r FROM Rating r WHERE r.active = true " +
           "AND (:professorId IS NULL OR r.professor.id = :professorId) " +
           "AND (:courseId IS NULL OR r.course.id = :courseId) " +
           "AND (:userId IS NULL OR r.user.id = :userId) " +
           "AND (:minRating IS NULL OR r.overallRating >= :minRating) " +
           "ORDER BY r.createdAt DESC")
    Page<Rating> findWithFilters(@Param("professorId") Long professorId,
                                @Param("courseId") Long courseId,
                                @Param("userId") Long userId,
                                @Param("minRating") Double minRating,
                                Pageable pageable);
}