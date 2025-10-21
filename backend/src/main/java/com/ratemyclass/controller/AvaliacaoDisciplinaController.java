package com.ratemyclass.controller;

import com.ratemyclass.dto.avaliacao.AvaliacaoDisciplinaRequestDTO;
import com.ratemyclass.entity.AvaliacaoDisciplina;
import com.ratemyclass.service.AvaliacaoDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes/disciplina")
public class AvaliacaoDisciplinaController {

    @Autowired
    private AvaliacaoDisciplinaService service;

    @GetMapping
    public ResponseEntity<List<AvaliacaoDisciplina>> listarAvaliacoes() {
        List<AvaliacaoDisciplina> avaliacoes = service.listarAvaliacoes();
        return ResponseEntity.ok(avaliacoes);
    }

    @PostMapping
    public ResponseEntity<String> criarAvaliacao(@RequestBody AvaliacaoDisciplinaRequestDTO request) {
        service.criarAvaliacao(request);
        return ResponseEntity.ok("Avaliação para disciplina cadastrada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAvaliacao(@PathVariable Long id) {
        service.deletarAvaliacao(id);
        return ResponseEntity.ok("Avaliação de disciplina removida (desativada) com sucesso!");
    }
}