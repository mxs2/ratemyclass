package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoProfessorRequestDTO;
import com.ratemyclass.entity.AvaliacaoProfessor;
import com.ratemyclass.repository.AvaliacaoProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoProfessorService {

    @Autowired // injeção de dependencias, sem precisa usar o new para instaciar uma classe
    private AvaliacaoProfessorRepository repository;

    public AvaliacaoProfessor criarAvaliacao(AvaliacaoProfessorRequestDTO request) {
        AvaliacaoProfessor avaliacao = new AvaliacaoProfessor();
        avaliacao.setProfessorId(request.getProfessorId());
        avaliacao.setDidatica(request.getDidatica());
        avaliacao.setQualidadeAula(request.getQualidadeAula());
        avaliacao.setFlexibilidade(request.getFlexibilidade());
        avaliacao.setOrganizacao(request.getOrganizacao());
        avaliacao.setComentario(request.getComentario());
        avaliacao.setVisibilidade(request.getVisibilidade());

        return repository.save(avaliacao);
    }
}
