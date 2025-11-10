package com.ratemyclass.dto.avaliacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoDisciplinaUpdateDTO {
    private Integer dificuldade;
    private Integer metodologia;
    private Integer conteudos;
    private Integer aplicabilidade;
    private String comentario;
}
