package com.ratemyclass.repository;

import com.ratemyclass.entity.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query("SELECT p FROM Professor p WHERE p.department.id = :deptId AND p.active = true")
    Page<Professor> findByDepartmentId(@Param("deptId") Long departmentId, Pageable pageable);

    @Query("SELECT p FROM Professor p WHERE p.department.code = :deptCode AND p.active = true")
    Page<Professor> findByDepartmentCode(@Param("deptCode") String departmentCode, Pageable pageable);

    @Query("SELECT p FROM Professor p WHERE p.active = true AND " +
           "(LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Professor> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query("SELECT p FROM Professor p " +
           "LEFT JOIN p.ratings r " +
           "WHERE p.active = true " +
           "GROUP BY p.id " +
           "HAVING AVG(r.overallRating) >= :minRating")
    Page<Professor> findByMinimumAverageRating(@Param("minRating") Double minRating, Pageable pageable);

    @Query("SELECT p FROM Professor p " +
           "LEFT JOIN p.ratings r " +
           "WHERE p.active = true " +
           "GROUP BY p.id " +
           "ORDER BY AVG(r.overallRating) DESC")
    Page<Professor> findTopRatedProfessors(Pageable pageable);

    @Query("SELECT p FROM Professor p " +
           "JOIN p.courses c " +
           "WHERE c.id = :courseId AND p.active = true")
    List<Professor> findByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT DISTINCT p FROM Professor p " +
           "LEFT JOIN p.ratings r " +
           "WHERE p.active = true " +
           "AND (:departmentId IS NULL OR p.department.id = :departmentId) " +
           "AND (:minRating IS NULL OR " +
           "   (SELECT AVG(r2.overallRating) FROM Rating r2 WHERE r2.professor = p) >= :minRating) " +
           "AND (:name IS NULL OR " +
           "   LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "   LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Professor> findWithFilters(@Param("departmentId") Long departmentId,
                                   @Param("minRating") Double minRating,
                                   @Param("name") String name,
                                   Pageable pageable);
}