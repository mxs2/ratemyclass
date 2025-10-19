package com.ratemyclass.dto.avaliacao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AvaliacaoCoordenadorRequestDTO {
    private Long coordenadorId;
    private Integer transparencia;
    private Integer interacaoAluno;
    private Integer suporte;
    private Integer organizacao;
    private String comentario;
    private String visibilidade; // "publica" ou "privada"
}
