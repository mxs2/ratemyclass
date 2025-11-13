package com.ratemyclass.controller;

import com.ratemyclass.dto.avaliacao.AvaliacaoCoordenadorRequestDTO;
import com.ratemyclass.dto.avaliacao.AvaliacaoCoordenadorUpdateDTO;
import com.ratemyclass.entity.AvaliacaoCoordenador;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.service.AvaliacaoCoordenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes/coordenador")
public class AvaliacaoCoordenadorController {

    @Autowired
    private AvaliacaoCoordenadorService service;

    // LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<AvaliacaoCoordenador>> listarAvaliacoes() {
        List<AvaliacaoCoordenador> avaliacoes = service.listarAvaliacoes();
        return ResponseEntity.ok(avaliacoes);
    }

    // BUSCAR POR ID (NOVO) – para edição
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoCoordenador> buscarPorId(@PathVariable Long id) {
        AvaliacaoCoordenador avaliacao = service.buscarPorId(id);
        return ResponseEntity.ok(avaliacao);
    }

    // CRIAR
    @PostMapping
    public ResponseEntity<String> criarAvaliacao(@RequestBody AvaliacaoCoordenadorRequestDTO request) {
        service.criarAvaliacao(request);
        return ResponseEntity.ok("Avaliação de coordenador cadastrada com sucesso!");
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAvaliacao(@PathVariable Long id) {
        service.deletarAvaliacao(id);
        return ResponseEntity.ok("Avaliação de coordenador removida (desativada) com sucesso!");
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAvaliacao(
            @PathVariable Long id,
            @RequestBody AvaliacaoCoordenadorUpdateDTO request) {
        try {
            service.atualizarAvaliacao(id, request);
            return ResponseEntity.ok("Avaliação atualizada com sucesso!");
        } catch (AvaliacaoInvalidaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}