package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoDisciplinaRequestDTO;
import com.ratemyclass.entity.AvaliacaoDisciplina;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.repository.AvaliacaoDisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvaliacaoDisciplinaService {

    @Autowired
    private AvaliacaoDisciplinaRepository repository;

    public void criarAvaliacao(AvaliacaoDisciplinaRequestDTO request) {

        validarCamposObrigatorios(request);

        AvaliacaoDisciplina avaliacao = new AvaliacaoDisciplina();
        avaliacao.setDisciplinaId(request.getDisciplinaId());
        avaliacao.setDificuldade(request.getDificuldade());
        avaliacao.setMetodologia(request.getMetodologia());
        avaliacao.setConteudos(request.getConteudos());
        avaliacao.setAplicabilidade(request.getAplicabilidade());
        avaliacao.setComentario(request.getComentario());
        avaliacao.setVisibilidade(request.getVisibilidade());

        repository.save(avaliacao);
    }

    public void validarCamposObrigatorios(AvaliacaoDisciplinaRequestDTO request) {
        List<String> camposFaltando = new ArrayList<>();

        if(request.getDisciplinaId() == null) camposFaltando.add("disciplinaId");
        if(request.getDificuldade() == null) camposFaltando.add("dificuldade");
        if(request.getMetodologia() == null) camposFaltando.add("metodologia");
        if(request.getConteudos() == null) camposFaltando.add("conteudos");
        if(request.getAplicabilidade() == null) camposFaltando.add("aplicabilidade");

        if (!camposFaltando.isEmpty()) {
            String message ="Campos obrigatórios estão vazios ou preenchidos incorretamente: "
                    + String.join(", ", camposFaltando);
            throw new AvaliacaoInvalidaException(message);
        }
    }
}
