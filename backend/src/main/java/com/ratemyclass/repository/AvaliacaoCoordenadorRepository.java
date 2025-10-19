package com.ratemyclass.repository;

import com.ratemyclass.entity.AvaliacaoCoordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoCoordenadorRepository extends JpaRepository<AvaliacaoCoordenador, Long> {
}
