package com.ratemyclass.controller;

import com.ratemyclass.dto.avaliacao.AvaliacaoResponseDTO;
import com.ratemyclass.entity.enums.TipoAvaliacao;
import com.ratemyclass.service.FiltroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filtro")
@RequiredArgsConstructor
public class FiltroController {
    
    private final FiltroService filtroService;

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> filtrarAvaliacoes(
            @RequestParam(required = false) TipoAvaliacao tipo,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false, defaultValue = "desc") String order
    ) {
        List<AvaliacaoResponseDTO> avaliacoes = filtroService.filtrarAvaliacoes(tipo, orderBy, order);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> filtrarPorTipo(
            @PathVariable TipoAvaliacao tipo
    ) {
        List<AvaliacaoResponseDTO> avaliacoes = filtroService.filtrarPorTipo(tipo);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarRecentes(
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        List<AvaliacaoResponseDTO> avaliacoes = filtroService.listarAvaliacoesRecentes(limit);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/populares")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPopulares(
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        List<AvaliacaoResponseDTO> avaliacoes = filtroService.listarAvaliacoesPopulares(limit);
        return ResponseEntity.ok(avaliacoes);
    }
}
