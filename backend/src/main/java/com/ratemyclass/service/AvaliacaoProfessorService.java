package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoProfessorRequestDTO;
import com.ratemyclass.entity.AvaliacaoProfessor;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.repository.AvaliacaoProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvaliacaoProfessorService {

    @Autowired // injeção de dependencias, sem precisa usar o new para instaciar uma classe
    private AvaliacaoProfessorRepository repository;

    public void criarAvaliacao(AvaliacaoProfessorRequestDTO request) {

        validarCamposObrigatorios(request);

        AvaliacaoProfessor avaliacao = new AvaliacaoProfessor();
        avaliacao.setProfessorId(request.getProfessorId());
        avaliacao.setDidatica(request.getDidatica());
        avaliacao.setQualidadeAula(request.getQualidadeAula());
        avaliacao.setFlexibilidade(request.getFlexibilidade());
        avaliacao.setOrganizacao(request.getOrganizacao());
        avaliacao.setComentario(request.getComentario());
        avaliacao.setVisibilidade(request.getVisibilidade());

        repository.save(avaliacao);
    }

    private void validarCamposObrigatorios(AvaliacaoProfessorRequestDTO request) {
        List<String> camposFaltando = new ArrayList<>();

        if (request.getProfessorId() == null) camposFaltando.add("professorId");
        if (request.getDidatica() == null) camposFaltando.add("didatica");
        if (request.getQualidadeAula() == null) camposFaltando.add("qualidadeAula");
        if (request.getFlexibilidade() == null) camposFaltando.add("flexibilidade");
        if (request.getOrganizacao() == null) camposFaltando.add("organizacao");

        if (!camposFaltando.isEmpty()) {
            String mensagem = "Campos obrigatórios estão vazios ou preenchidos incorretamente: "
                    + String.join(", ", camposFaltando);
            throw new AvaliacaoInvalidaException(mensagem);
        }
    }
}
