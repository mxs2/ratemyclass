package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoCoordenadorRequestDTO;
import com.ratemyclass.dto.avaliacao.AvaliacaoCoordenadorUpdateDTO;
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
        validarCamposObrigatorios(request);
        validarNotas(request.getTransparencia(), request.getInteracaoAluno(), request.getSuporte(), request.getOrganizacao());

        AvaliacaoCoordenador avaliacao = new AvaliacaoCoordenador();
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

    private void validarCamposObrigatorios(AvaliacaoCoordenadorRequestDTO request) {
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

    private void validarNotas(Integer transparencia, Integer interacaoAluno, Integer suporte, Integer organizacao) {
        if (!isNotaValida(transparencia)) throw new AvaliacaoInvalidaException("Transparência deve ser entre 0 e 10.");
        if (!isNotaValida(interacaoAluno)) throw new AvaliacaoInvalidaException("Interação com aluno deve ser entre 0 e 10.");
        if (!isNotaValida(suporte)) throw new AvaliacaoInvalidaException("Suporte deve ser entre 0 e 10.");
        if (!isNotaValida(organizacao)) throw new AvaliacaoInvalidaException("Organização deve ser entre 0 e 10.");
    }

    private boolean isNotaValida(Integer nota) {
        return nota != null && nota >= 0 && nota <= 10;
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

    public void atualizarAvaliacao(Long id, AvaliacaoCoordenadorUpdateDTO request) {
        Optional<AvaliacaoCoordenador> avaliacaoOpt = repository.findById(id);

        if (avaliacaoOpt.isEmpty() || !avaliacaoOpt.get().isActive()) {
            throw new AvaliacaoInvalidaException("Avaliação não encontrada ou já desativada.");
        }

        AvaliacaoCoordenador avaliacao = avaliacaoOpt.get();

        // Validação: só atualiza se não nulo, e deve ser 0-10
        if (request.getTransparencia() != null) {
            if (!isNotaValida(request.getTransparencia()))
                throw new AvaliacaoInvalidaException("Transparência deve ser entre 0 e 10.");
            avaliacao.setTransparencia(request.getTransparencia());
        }

        if (request.getInteracaoAluno() != null) {
            if (!isNotaValida(request.getInteracaoAluno()))
                throw new AvaliacaoInvalidaException("Interação com aluno deve ser entre 0 e 10.");
            avaliacao.setInteracaoAluno(request.getInteracaoAluno());
        }

        if (request.getSuporte() != null) {
            if (!isNotaValida(request.getSuporte()))
                throw new AvaliacaoInvalidaException("Suporte deve ser entre 0 e 10.");
            avaliacao.setSuporte(request.getSuporte());
        }

        if (request.getOrganizacao() != null) {
            if (!isNotaValida(request.getOrganizacao()))
                throw new AvaliacaoInvalidaException("Organização deve ser entre 0 e 10.");
            avaliacao.setOrganizacao(request.getOrganizacao());
        }

        if (request.getComentario() != null) {
            avaliacao.setComentario(request.getComentario());
        }

        repository.save(avaliacao);
        System.out.println("Avaliação ID " + id + " atualizada com sucesso.");
    }
}
