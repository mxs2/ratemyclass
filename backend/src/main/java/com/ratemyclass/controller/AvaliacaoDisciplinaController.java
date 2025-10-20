package com.ratemyclass.controller;


import com.ratemyclass.dto.avaliacao.AvaliacaoDisciplinaRequestDTO;
import com.ratemyclass.entity.AvaliacaoDisciplina;
import com.ratemyclass.entity.AvaliacaoProfessor;
import com.ratemyclass.service.AvaliacaoDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/avaliacoes/disciplina")
public class AvaliacaoDisciplinaController {

    @Autowired
    private AvaliacaoDisciplinaService service;

    @PostMapping
    public ResponseEntity<String> criarAvaliacao( @RequestBody AvaliacaoDisciplinaRequestDTO request ) {
        service.criarAvaliacao(request);
        return ResponseEntity.ok("Avaliação para disciplina cadastrada com sucesso!");
    };
}
