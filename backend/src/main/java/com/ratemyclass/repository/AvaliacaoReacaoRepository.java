package com.ratemyclass.repository;

import com.ratemyclass.entity.AvaliacaoReacao;
import com.ratemyclass.entity.enums.TipoAvaliacao;
import com.ratemyclass.entity.enums.TipoReacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AvaliacaoReacaoRepository extends JpaRepository<AvaliacaoReacao, Long> {

    Optional<AvaliacaoReacao> findByUsuarioIdAndAvaliacaoIdAndTipoAvaliacao(
            Long usuarioId,
            Long avaliacaoId,
            TipoAvaliacao tipoAvaliacao
    );

    @Query("""
            SELECT COUNT(r) 
            FROM AvaliacaoReacao r 
            WHERE r.avaliacaoId = :avaliacaoId
              AND r.tipoAvaliacao = :tipoAvaliacao
              AND r.tipoReacao = com.ratemyclass.entity.enums.TipoReacao.LIKE
            """)
    Long countLikes(Long avaliacaoId, TipoAvaliacao tipoAvaliacao);

    @Query("""
            SELECT COUNT(r) 
            FROM AvaliacaoReacao r 
            WHERE r.avaliacaoId = :avaliacaoId
              AND r.tipoAvaliacao = :tipoAvaliacao
              AND r.tipoReacao = com.ratemyclass.entity.enums.TipoReacao.DISLIKE
            """)
    Long countDeslikes(Long avaliacaoId, TipoAvaliacao tipoAvaliacao);

    void deleteByAvaliacaoId(Long avaliacaoId);
}
