package com.ratemyclass.controller;


import com.ratemyclass.dto.avaliacao.AvaliacaoCoordenadorRequestDTO;
import com.ratemyclass.entity.AvaliacaoCoordenador;
import com.ratemyclass.service.AvaliacaoCoordenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("avaliacoes/coordenador")
public class AvaliacaoCoordenadorController {

    @Autowired
    private AvaliacaoCoordenadorService service;

    @PostMapping
    public ResponseEntity<String> criarAvaliacao(@RequestBody AvaliacaoCoordenadorRequestDTO request) {
        service.criarAvaliacao(request);

        return ResponseEntity.ok("Avaliação para coordenador cadastrada com sucesso!");
    }
}
