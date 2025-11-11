package com.ratemyclass.dto.reacao;

import com.ratemyclass.entity.enums.TipoAvaliacao;
import com.ratemyclass.entity.enums.TipoReacao;

public record AvaliacaoReacaoRequest(
        Long avaliacaoId,
        TipoAvaliacao tipoAvaliacao,
        TipoReacao tipoReacao
) {
}