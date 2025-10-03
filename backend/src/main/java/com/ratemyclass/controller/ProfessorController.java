package com.ratemyclass.controller;

import com.ratemyclass.dto.professor.ProfessorResponseDTO;
import com.ratemyclass.entity.Professor;
import com.ratemyclass.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/professors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProfessorController {
    
    private final ProfessorService professorService;
    
    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }
    
    /**
     * Get all professors with pagination
     */
    @GetMapping
    public ResponseEntity<Page<ProfessorResponseDTO>> getAllProfessors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<Professor> professors = professorService.getAllProfessors(pageable);
        Page<ProfessorResponseDTO> professorsDTO = professors.map(ProfessorResponseDTO::new);
        
        return ResponseEntity.ok(professorsDTO);
    }
    
    /**
     * Get professor by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getProfessorById(@PathVariable Long id) {
        Optional<Professor> professor = professorService.getProfessorById(id);
        
        if (professor.isPresent()) {
            return ResponseEntity.ok(new ProfessorResponseDTO(professor.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Search professors by name
     */
    @GetMapping("/search")
    public ResponseEntity<Page<ProfessorResponseDTO>> searchProfessors(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<Professor> professors = professorService.searchProfessorsByName(q, pageable);
        Page<ProfessorResponseDTO> professorsDTO = professors.map(ProfessorResponseDTO::new);
        
        return ResponseEntity.ok(professorsDTO);
    }
    
    /**
     * Get professors by department
     */
    @GetMapping("/department/{departmentCode}")
    public ResponseEntity<Page<ProfessorResponseDTO>> getProfessorsByDepartment(
            @PathVariable String departmentCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<Professor> professors = professorService.getProfessorsByDepartmentCode(departmentCode, pageable);
        Page<ProfessorResponseDTO> professorsDTO = professors.map(ProfessorResponseDTO::new);
        
        return ResponseEntity.ok(professorsDTO);
    }
    
    /**
     * Get top rated professors
     */
    @GetMapping("/top-rated")
    public ResponseEntity<Page<ProfessorResponseDTO>> getTopRatedProfessors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Professor> professors = professorService.getTopRatedProfessors(pageable);
        Page<ProfessorResponseDTO> professorsDTO = professors.map(ProfessorResponseDTO::new);
        
        return ResponseEntity.ok(professorsDTO);
    }
    
    /**
     * Get professors by course
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ProfessorResponseDTO>> getProfessorsByCourse(@PathVariable Long courseId) {
        List<Professor> professors = professorService.getProfessorsByCourseId(courseId);
        List<ProfessorResponseDTO> professorsDTO = professors.stream()
                .map(ProfessorResponseDTO::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(professorsDTO);
    }
    
    /**
     * Advanced search with filters
     */
    @GetMapping("/filter")
    public ResponseEntity<Page<ProfessorResponseDTO>> searchWithFilters(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<Professor> professors = professorService.searchProfessorsWithFilters(departmentId, minRating, name, pageable);
        Page<ProfessorResponseDTO> professorsDTO = professors.map(ProfessorResponseDTO::new);
        
        return ResponseEntity.ok(professorsDTO);
    }
}