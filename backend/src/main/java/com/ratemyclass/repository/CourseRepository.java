package com.ratemyclass.repository;

import com.ratemyclass.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCode(String code);

    @Query("SELECT c FROM Course c WHERE c.department.id = :deptId AND c.active = true")
    Page<Course> findByDepartmentId(@Param("deptId") Long departmentId, Pageable pageable);

    @Query("SELECT c FROM Course c WHERE c.department.code = :deptCode AND c.active = true")
    Page<Course> findByDepartmentCode(@Param("deptCode") String departmentCode, Pageable pageable);

    @Query("SELECT c FROM Course c WHERE c.active = true AND " +
           "(LOWER(c.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Course> findByCodeOrNameContainingIgnoreCase(@Param("search") String search, Pageable pageable);

    @Query("SELECT c FROM Course c " +
           "LEFT JOIN c.ratings r " +
           "WHERE c.active = true " +
           "GROUP BY c.id " +
           "HAVING AVG(r.overallRating) >= :minRating")
    Page<Course> findByMinimumAverageRating(@Param("minRating") Double minRating, Pageable pageable);

    @Query("SELECT c FROM Course c " +
           "LEFT JOIN c.ratings r " +
           "WHERE c.active = true " +
           "GROUP BY c.id " +
           "ORDER BY AVG(r.overallRating) DESC")
    Page<Course> findTopRatedCourses(Pageable pageable);

    @Query("SELECT c FROM Course c " +
           "JOIN c.professors p " +
           "WHERE p.id = :professorId AND c.active = true")
    List<Course> findByProfessorId(@Param("professorId") Long professorId);

    @Query("SELECT c FROM Course c WHERE c.credits = :credits AND c.active = true")
    Page<Course> findByCredits(@Param("credits") Integer credits, Pageable pageable);

    @Query("SELECT DISTINCT c FROM Course c " +
           "LEFT JOIN c.ratings r " +
           "WHERE c.active = true " +
           "AND (:departmentId IS NULL OR c.department.id = :departmentId) " +
           "AND (:minRating IS NULL OR " +
           "   (SELECT AVG(r2.overallRating) FROM Rating r2 WHERE r2.course = c) >= :minRating) " +
           "AND (:credits IS NULL OR c.credits = :credits) " +
           "AND (:search IS NULL OR " +
           "   LOWER(c.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "   LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Course> findWithFilters(@Param("departmentId") Long departmentId,
                                @Param("minRating") Double minRating,
                                @Param("credits") Integer credits,
                                @Param("search") String search,
                                Pageable pageable);
}