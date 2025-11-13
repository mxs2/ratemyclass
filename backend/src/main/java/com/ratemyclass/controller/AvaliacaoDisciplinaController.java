package com.ratemyclass.controller;

import com.ratemyclass.dto.avaliacao.AvaliacaoDisciplinaRequestDTO;
import com.ratemyclass.dto.avaliacao.AvaliacaoDisciplinaUpdateDTO;
import com.ratemyclass.entity.AvaliacaoDisciplina;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
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

    // LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<AvaliacaoDisciplina>> listarAvaliacoes() {
        List<AvaliacaoDisciplina> avaliacoes = service.listarAvaliacoes();
        return ResponseEntity.ok(avaliacoes);
    }

    // BUSCAR POR ID (NOVO)
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDisciplina> buscarPorId(@PathVariable Long id) {
        AvaliacaoDisciplina avaliacao = service.buscarPorId(id);
        return ResponseEntity.ok(avaliacao);
    }

    // CRIAR
    @PostMapping
    public ResponseEntity<String> criarAvaliacao(@RequestBody AvaliacaoDisciplinaRequestDTO request) {
        service.criarAvaliacao(request);
        return ResponseEntity.ok("Avaliação para disciplina cadastrada com sucesso!");
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAvaliacao(@PathVariable Long id) {
        service.deletarAvaliacao(id);
        return ResponseEntity.ok("Avaliação de disciplina removida (desativada) com sucesso!");
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAvaliacao(
            @PathVariable Long id,
            @RequestBody AvaliacaoDisciplinaUpdateDTO request) {
        try {
            service.atualizarAvaliacao(id, request);
            return ResponseEntity.ok("Avaliação atualizada com sucesso!");
        } catch (AvaliacaoInvalidaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}