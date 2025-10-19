package com.ratemyclass.dto.avaliacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoProfessorRequestDTO {
    private Long professorId;
    private Integer didatica;
    private Integer qualidadeAula;
    private Integer flexibilidade;
    private Integer organizacao;
    private String comentario;
    private String visibilidade; // "publica" ou "privada"
}
