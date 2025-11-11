package com.ratemyclass.repository;

import com.ratemyclass.entity.AvaliacaoCoordenador;
import com.ratemyclass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvaliacaoCoordenadorRepository extends JpaRepository<AvaliacaoCoordenador, Long> {

    List<AvaliacaoCoordenador> findByActiveTrue();

    List<AvaliacaoCoordenador> findByCoordenadorIdAndActiveTrue(Long coordenadorId);

    List<AvaliacaoCoordenador> findByUsuarioAndActiveTrue(User usuario);
}
