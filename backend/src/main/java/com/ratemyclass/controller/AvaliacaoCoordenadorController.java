package com.ratemyclass.controller;

import com.ratemyclass.dto.avaliacao.AvaliacaoCoordenadorRequestDTO;
import com.ratemyclass.dto.avaliacao.AvaliacaoCoordenadorUpdateDTO;
import com.ratemyclass.entity.AvaliacaoCoordenador;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.service.AvaliacaoCoordenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("avaliacoes/coordenador")
public class AvaliacaoCoordenadorController {

    @Autowired
    private AvaliacaoCoordenadorService service;

    @GetMapping
    public ResponseEntity<List<AvaliacaoCoordenador>> listarAvaliacoes() {
        List<AvaliacaoCoordenador> avaliacoes = service.listarAvaliacoes();
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/{coordenadorId}")
    public ResponseEntity<List<AvaliacaoCoordenador>> listarPorCoordenador(@PathVariable Long coordenadorId) {
        List<AvaliacaoCoordenador> avaliacoes = service.listarPorCoordenador(coordenadorId);
        return ResponseEntity.ok(avaliacoes);
    }

    @PostMapping
    public ResponseEntity<String> criarAvaliacao(@RequestBody AvaliacaoCoordenadorRequestDTO request) {
        service.criarAvaliacao(request);
        return ResponseEntity.ok("Avaliação para coordenador cadastrada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAvaliacao(@PathVariable Long id) {
        service.deletarAvaliacao(id);
        return ResponseEntity.ok("Avaliação de coordenador removida (desativada) com sucesso!");
    }

    // ------------------- NOVO MÉTODO DE UPDATE -------------------
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAvaliacao(
            @PathVariable Long id,
            @RequestBody AvaliacaoCoordenadorUpdateDTO request
    ) {
        try {
            service.atualizarAvaliacao(id, request);
            return ResponseEntity.ok("Avaliação atualizada com sucesso!");
        } catch (AvaliacaoInvalidaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}