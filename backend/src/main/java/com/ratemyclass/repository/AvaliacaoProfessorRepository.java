package com.ratemyclass.repository;

import com.ratemyclass.entity.AvaliacaoProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoProfessorRepository  extends JpaRepository<AvaliacaoProfessor, Long> {
                                                                    // Entidade        //id da tabela
}
