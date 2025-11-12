package com.ratemyclass.controller;

import com.ratemyclass.dto.reacao.AvaliacaoReacaoRequest;
import com.ratemyclass.service.AvaliacaoReacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reacoes")
@RequiredArgsConstructor
public class AvaliacaoReacaoController {

    private final AvaliacaoReacaoService service;

    @PostMapping
    public ResponseEntity<Void> reagir( @RequestBody AvaliacaoReacaoRequest request) {
        service.toggleReacao(
                request.avaliacaoId(),
                request.tipoAvaliacao(),
                request.tipoReacao()
        );

        return ResponseEntity.ok().build();
    }
}