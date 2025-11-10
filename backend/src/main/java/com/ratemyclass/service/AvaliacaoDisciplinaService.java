package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoDisciplinaRequestDTO;
import com.ratemyclass.dto.avaliacao.AvaliacaoDisciplinaUpdateDTO;
import com.ratemyclass.entity.AvaliacaoDisciplina;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.repository.AvaliacaoDisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoDisciplinaService {

    @Autowired
    private AvaliacaoDisciplinaRepository repository;

    public List<AvaliacaoDisciplina> listarAvaliacoes() {
        return repository.findByActiveTrue();
    }

    public void criarAvaliacao(AvaliacaoDisciplinaRequestDTO request) {
        validarCamposObrigatorios(request);
        validarNotas(request.getDificuldade(), request.getMetodologia(), request.getConteudos(), request.getAplicabilidade());

        AvaliacaoDisciplina avaliacao = new AvaliacaoDisciplina();
        avaliacao.setDisciplinaId(request.getDisciplinaId());
        avaliacao.setDificuldade(request.getDificuldade());
        avaliacao.setMetodologia(request.getMetodologia());
        avaliacao.setConteudos(request.getConteudos());
        avaliacao.setAplicabilidade(request.getAplicabilidade());
        avaliacao.setComentario(request.getComentario());
        avaliacao.setVisibilidade(request.getVisibilidade());
        avaliacao.setActive(true);

        repository.save(avaliacao);
    }

    public void deletarAvaliacao(Long id) {
        Optional<AvaliacaoDisciplina> avaliacaoOptional = repository.findById(id);

        if (avaliacaoOptional.isEmpty()) {
            throw new AvaliacaoInvalidaException("Avaliação de disciplina não encontrada para o ID: " + id);
        }

        AvaliacaoDisciplina avaliacao = avaliacaoOptional.get();

        if (!avaliacao.isActive()) {
            throw new AvaliacaoInvalidaException("A avaliação já foi desativada anteriormente.");
        }

        avaliacao.setActive(false);
        repository.save(avaliacao);
    }

    private void validarCamposObrigatorios(AvaliacaoDisciplinaRequestDTO request) {
        List<String> camposFaltando = new ArrayList<>();

        if (request.getDisciplinaId() == null) camposFaltando.add("disciplinaId");
        if (request.getDificuldade() == null) camposFaltando.add("dificuldade");
        if (request.getMetodologia() == null) camposFaltando.add("metodologia");
        if (request.getConteudos() == null) camposFaltando.add("conteudos");
        if (request.getAplicabilidade() == null) camposFaltando.add("aplicabilidade");

        if (!camposFaltando.isEmpty()) {
            String message = "Campos obrigatórios estão vazios ou preenchidos incorretamente: "
                    + String.join(", ", camposFaltando);
            throw new AvaliacaoInvalidaException(message);
        }
    }

    private void validarNotas(Integer dificuldade, Integer metodologia, Integer conteudos, Integer aplicabilidade) {
        if (dificuldade != null && (dificuldade < 1 || dificuldade > 5)) {
            throw new AvaliacaoInvalidaException("Dificuldade deve ser entre 1 e 5.");
        }
        if (metodologia != null && (metodologia < 1 || metodologia > 5)) {
            throw new AvaliacaoInvalidaException("Metodologia deve ser entre 1 e 5.");
        }
        if (conteudos != null && (conteudos < 1 || conteudos > 5)) {
            throw new AvaliacaoInvalidaException("Conteúdos deve ser entre 1 e 5.");
        }
        if (aplicabilidade != null && (aplicabilidade < 1 || aplicabilidade > 5)) {
            throw new AvaliacaoInvalidaException("Aplicabilidade deve ser entre 1 e 5.");
        }
    }

    // ------------------- NOVO MÉTODO DE UPDATE -------------------
    public void atualizarAvaliacao(Long id, AvaliacaoDisciplinaUpdateDTO request) {
        Optional<AvaliacaoDisciplina> avaliacaoOpt = repository.findById(id);

        if (avaliacaoOpt.isEmpty() || !avaliacaoOpt.get().isActive()) {
            throw new AvaliacaoInvalidaException("Avaliação não encontrada ou já desativada.");
        }

        AvaliacaoDisciplina avaliacao = avaliacaoOpt.get();

        if (request.getDificuldade() != null) {
            validarNotas(request.getDificuldade(), null, null, null);
            avaliacao.setDificuldade(request.getDificuldade());
        }
        if (request.getMetodologia() != null) {
            validarNotas(null, request.getMetodologia(), null, null);
            avaliacao.setMetodologia(request.getMetodologia());
        }
        if (request.getConteudos() != null) {
            validarNotas(null, null, request.getConteudos(), null);
            avaliacao.setConteudos(request.getConteudos());
        }
        if (request.getAplicabilidade() != null) {
            validarNotas(null, null, null, request.getAplicabilidade());
            avaliacao.setAplicabilidade(request.getAplicabilidade());
        }
        if (request.getComentario() != null) {
            avaliacao.setComentario(request.getComentario());
        }

        repository.save(avaliacao);

        System.out.println("Avaliação ID " + id + " atualizada com sucesso.");
    }
}