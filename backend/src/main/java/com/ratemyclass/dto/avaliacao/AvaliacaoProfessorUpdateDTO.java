package com.ratemyclass.dto.avaliacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoProfessorUpdateDTO {
    private Integer didatica;
    private Integer qualidadeAula;
    private Integer flexibilidade;
    private Integer organizacao;
    private String comentario;
}