package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoDisciplinaRequestDTO;
import com.ratemyclass.entity.AvaliacaoDisciplina;
import com.ratemyclass.repository.AvaliacaoDisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoDisciplinaService {

    @Autowired
    private AvaliacaoDisciplinaRepository repository;

    public AvaliacaoDisciplina criarAvaliacao(AvaliacaoDisciplinaRequestDTO request) {
        AvaliacaoDisciplina avaliacao = new AvaliacaoDisciplina();
        avaliacao.setDisciplinaId(request.getDisciplinaId());
        avaliacao.setDificuldade(request.getDificuldade());
        avaliacao.setMetodologia(request.getMetodologia());
        avaliacao.setConteudos(request.getConteudos());
        avaliacao.setAplicabilidade(request.getAplicabilidade());
        avaliacao.setComentario(request.getComentario());
        avaliacao.setVisibilidade(request.getVisibilidade());

        return repository.save(avaliacao);
    }
}
