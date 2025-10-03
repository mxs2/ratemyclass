package com.ratemyclass.service;

import com.ratemyclass.entity.Professor;
import com.ratemyclass.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public Page<Professor> getAllProfessors(Pageable pageable) {
        return professorRepository.findAll(pageable);
    }

    public Optional<Professor> getProfessorById(Long id) {
        return professorRepository.findById(id);
    }

    public Page<Professor> getProfessorsByDepartmentId(Long departmentId, Pageable pageable) {
        return professorRepository.findByDepartmentId(departmentId, pageable);
    }

    public Page<Professor> getProfessorsByDepartmentCode(String departmentCode, Pageable pageable) {
        return professorRepository.findByDepartmentCode(departmentCode, pageable);
    }

    public Page<Professor> searchProfessorsByName(String name, Pageable pageable) {
        return professorRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Professor> getTopRatedProfessors(Pageable pageable) {
        return professorRepository.findTopRatedProfessors(pageable);
    }

    public Page<Professor> getProfessorsWithMinimumRating(Double minRating, Pageable pageable) {
        return professorRepository.findByMinimumAverageRating(minRating, pageable);
    }

    public List<Professor> getProfessorsByCourseId(Long courseId) {
        return professorRepository.findByCourseId(courseId);
    }

    public Page<Professor> searchProfessorsWithFilters(Long departmentId, Double minRating, String name, Pageable pageable) {
        return professorRepository.findWithFilters(departmentId, minRating, name, pageable);
    }

    @Transactional
    public Professor saveProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    @Transactional
    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }

    @Transactional
    public void deactivateProfessor(Long id) {
        Optional<Professor> professor = professorRepository.findById(id);
        if (professor.isPresent()) {
            professor.get().setActive(false);
            professorRepository.save(professor.get());
        }
    }
}