package com.ratemyclass.controller;

import com.ratemyclass.dto.avaliacao.AvaliacaoProfessorRequestDTO;
import com.ratemyclass.dto.avaliacao.AvaliacaoProfessorUpdateDTO;
import com.ratemyclass.entity.AvaliacaoProfessor;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.service.AvaliacaoProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes/professor")
public class AvaliacaoProfessorController {

    @Autowired
    private AvaliacaoProfessorService service;

    // LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<AvaliacaoProfessor>> listarAvaliacoes() {
        List<AvaliacaoProfessor> avaliacoes = service.listarAvaliacoes();
        return ResponseEntity.ok(avaliacoes);
    }

    // BUSCAR POR ID (NOVO MÉTODO)
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoProfessor> buscarAvaliacaoPorId(@PathVariable Long id) {
        AvaliacaoProfessor avaliacao = service.buscarPorId(id);
        return ResponseEntity.ok(avaliacao);
    }

    // CRIAR
    @PostMapping
    public ResponseEntity<String> criarAvaliacao(@RequestBody AvaliacaoProfessorRequestDTO request) {
        service.criarAvaliacao(request);
        return ResponseEntity.ok("Avaliação para professor cadastrada com sucesso!");
    }

    // ATUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAvaliacao(
            @PathVariable Long id,
            @RequestBody AvaliacaoProfessorUpdateDTO request) {
        try {
            service.atualizarAvaliacao(id, request);
            return ResponseEntity.ok("Avaliação atualizada com sucesso!");
        } catch (AvaliacaoInvalidaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAvaliacao(@PathVariable Long id) {
        service.deletarAvaliacao(id);
        return ResponseEntity.ok("Avaliação de professor removida (desativada) com sucesso!");
    }
}