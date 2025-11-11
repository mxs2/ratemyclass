package com.ratemyclass.dto.avaliacao;

import com.ratemyclass.entity.enums.TipoAvaliacao;
import com.ratemyclass.entity.enums.TipoReacao;
import lombok.Builder;
import lombok.Data;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvaliacaoResponseDTO {
    private Long avaliacaoId;
    private TipoAvaliacao tipoAvaliacao;
    private String nomeReferencia;
    private String comentario;
    private Long likes;
    private Long deslikes;
    private TipoReacao userReaction;

    // PROFESSOR
    private Integer didatica;
    private Integer qualidadeAula;
    private Integer flexibilidade;
    private Integer organizacaoProfessor;

    // DISCIPLINA
    private Integer dificuldade;
    private Integer metodologia;
    private Integer conteudos;
    private Integer aplicabilidade;

    // COORDENADOR
    private Integer transparencia;
    private Integer interacaoAluno;
    private Integer suporte;
    private Integer organizacaoCoordenador;
}
