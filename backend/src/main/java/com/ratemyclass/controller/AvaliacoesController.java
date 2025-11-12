package com.ratemyclass.controller;

import com.ratemyclass.dto.avaliacao.AvaliacaoResponseDTO;
import com.ratemyclass.service.FeedAvaliacoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
@RequiredArgsConstructor
public class AvaliacoesController {
    private final FeedAvaliacoesService service;

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarAvaliacoes());
    }
}