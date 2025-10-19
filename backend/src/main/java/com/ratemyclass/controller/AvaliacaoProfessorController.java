package com.ratemyclass.controller;

import com.ratemyclass.dto.avaliacao.AvaliacaoProfessorRequestDTO;
import com.ratemyclass.entity.AvaliacaoProfessor;
import com.ratemyclass.service.AvaliacaoProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes/professor")
public class AvaliacaoProfessorController {

    @Autowired
    private AvaliacaoProfessorService service;

    @PostMapping
    public ResponseEntity<AvaliacaoProfessor> criarAvaliacao(@RequestBody AvaliacaoProfessorRequestDTO request) {
        AvaliacaoProfessor novaAvaliacao = service.criarAvaliacao(request);
        return ResponseEntity.ok(novaAvaliacao);
    }
}