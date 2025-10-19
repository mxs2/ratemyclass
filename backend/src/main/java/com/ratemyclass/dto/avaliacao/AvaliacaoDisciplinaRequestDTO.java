package com.ratemyclass.dto.avaliacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoDisciplinaRequestDTO {
    private Long disciplinaId;
    private Integer dificuldade;
    private Integer metodologia;
    private Integer conteudos;
    private Integer aplicabilidade;
    private String comentario;
    private String visibilidade; // "publica" ou "privada"
}