package com.ratemyclass.repository;

import com.ratemyclass.entity.AvaliacaoDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoDisciplinaRepository extends JpaRepository<AvaliacaoDisciplina, Long> {
    List<AvaliacaoDisciplina> findByActiveTrue();
}