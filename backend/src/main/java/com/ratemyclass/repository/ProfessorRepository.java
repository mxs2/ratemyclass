package com.ratemyclass.repository;

import com.ratemyclass.entity.Professor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;

@ReadingConverter
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
