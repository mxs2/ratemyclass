package com.ratemyclass.repository;

import com.ratemyclass.entity.AvaliacaoProfessor;
import com.ratemyclass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvaliacaoProfessorRepository extends JpaRepository<AvaliacaoProfessor, Long> {
    List<AvaliacaoProfessor> findByActiveTrue();

    List<AvaliacaoProfessor> findByUsuarioAndActiveTrue(User usuario);
}