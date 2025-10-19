package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoCoordenadorRequestDTO;
import com.ratemyclass.entity.AvaliacaoCoordenador;
import com.ratemyclass.repository.AvaliacaoCoordenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoCoordenadorService {

    @Autowired
    private AvaliacaoCoordenadorRepository repository;

    public AvaliacaoCoordenador criarAvaliacao(AvaliacaoCoordenadorRequestDTO request) {
        AvaliacaoCoordenador avaliacao = new AvaliacaoCoordenador();

        avaliacao.setCoordenadorId(request.getCoordenadorId());
        avaliacao.setTransparencia(request.getTransparencia());
        avaliacao.setInteracaoAluno(request.getInteracaoAluno());
        avaliacao.setSuporte(request.getSuporte());
        avaliacao.setOrganizacao(request.getOrganizacao());
        avaliacao.setComentario(request.getComentario());
        avaliacao.setVisibilidade(request.getVisibilidade());

        return repository.save(avaliacao);
    }
}
