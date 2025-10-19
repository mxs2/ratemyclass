package com.ratemyclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ratemyclass.entity.AvaliacaoDisciplina;

@Repository
public interface AvaliacaoDisciplinaRepository extends JpaRepository<AvaliacaoDisciplina, Long> {
}
