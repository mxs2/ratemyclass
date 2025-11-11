package com.ratemyclass.repository;

import com.ratemyclass.entity.AvaliacaoDisciplina;
import com.ratemyclass.entity.AvaliacaoProfessor;
import com.ratemyclass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoDisciplinaRepository extends JpaRepository<AvaliacaoDisciplina, Long> {
    List<AvaliacaoDisciplina> findByActiveTrue();

    List<AvaliacaoDisciplina> findByUsuarioAndActiveTrue(User usuario);
}