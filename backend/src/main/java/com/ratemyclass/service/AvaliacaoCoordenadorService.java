package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoCoordenadorRequestDTO;
import com.ratemyclass.entity.AvaliacaoCoordenador;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.repository.AvaliacaoCoordenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoCoordenadorService {

    @Autowired
    private AvaliacaoCoordenadorRepository repository;

    public List<AvaliacaoCoordenador> listarAvaliacoes() {
        return repository.findByActiveTrue();
    }

    public List<AvaliacaoCoordenador> listarPorCoordenador(Long coordenadorId) {
        return repository.findByCoordenadorIdAndActiveTrue(coordenadorId);
    }

    public void criarAvaliacao(AvaliacaoCoordenadorRequestDTO request) {
        AvaliacaoCoordenador avaliacao = new AvaliacaoCoordenador();

        validarCamposObrigatorios(request);

        avaliacao.setCoordenadorId(request.getCoordenadorId());
        avaliacao.setTransparencia(request.getTransparencia());
        avaliacao.setInteracaoAluno(request.getInteracaoAluno());
        avaliacao.setSuporte(request.getSuporte());
        avaliacao.setOrganizacao(request.getOrganizacao());
        avaliacao.setComentario(request.getComentario());
        avaliacao.setVisibilidade(request.getVisibilidade());
        avaliacao.setActive(true);

        repository.save(avaliacao);
    }

    public void validarCamposObrigatorios(AvaliacaoCoordenadorRequestDTO request) {
        List<String> camposFaltando = new ArrayList<>();

        if (request.getCoordenadorId() == null) camposFaltando.add("coordenadorId");
        if (request.getTransparencia() == null) camposFaltando.add("transparencia");
        if (request.getInteracaoAluno() == null) camposFaltando.add("interacaoAluno");
        if (request.getSuporte() == null) camposFaltando.add("suporte");
        if (request.getOrganizacao() == null) camposFaltando.add("organizacao");

        if (!camposFaltando.isEmpty()) {
            String message = "Campos obrigatórios estão vazios ou preenchidos incorretamente: "
                    + String.join(", ", camposFaltando);
            throw new AvaliacaoInvalidaException(message);
        }
    }

    public void deletarAvaliacao(Long id) {
        Optional<AvaliacaoCoordenador> avaliacaoOpt = repository.findById(id);

        if (avaliacaoOpt.isEmpty()) {
            throw new AvaliacaoInvalidaException("Avaliação não encontrada para o ID: " + id);
        }

        AvaliacaoCoordenador avaliacao = avaliacaoOpt.get();

        if (!avaliacao.isActive()) {
            throw new AvaliacaoInvalidaException("A avaliação já foi desativada anteriormente.");
        }

        avaliacao.setActive(false);
        repository.save(avaliacao);
    }
}