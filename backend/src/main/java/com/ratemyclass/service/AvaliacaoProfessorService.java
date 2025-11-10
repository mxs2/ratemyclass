package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoProfessorRequestDTO;
import com.ratemyclass.dto.avaliacao.AvaliacaoProfessorUpdateDTO;
import com.ratemyclass.entity.AvaliacaoProfessor;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.repository.AvaliacaoProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoProfessorService {

    @Autowired
    private AvaliacaoProfessorRepository repository;

    public List<AvaliacaoProfessor> listarAvaliacoes() {
        return repository.findByActiveTrue();
    }

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
        avaliacao.setActive(true);

        repository.save(avaliacao);
    }

    public void deletarAvaliacao(Long id) {
        Optional<AvaliacaoProfessor> avaliacaoOptional = repository.findById(id);

        if (avaliacaoOptional.isEmpty()) {
            throw new AvaliacaoInvalidaException("Avaliação de professor não encontrada para o ID: " + id);
        }

        AvaliacaoProfessor avaliacao = avaliacaoOptional.get();

        if (!avaliacao.isActive()) {
            throw new AvaliacaoInvalidaException("A avaliação já foi desativada anteriormente.");
        }

        avaliacao.setActive(false);
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

    // ------------------- NOVO MÉTODO DE UPDATE -------------------
    public void atualizarAvaliacao(Long id, AvaliacaoProfessorUpdateDTO request) {
        Optional<AvaliacaoProfessor> avaliacaoOpt = repository.findById(id);

        if (avaliacaoOpt.isEmpty() || !avaliacaoOpt.get().isActive()) {
            throw new AvaliacaoInvalidaException("Avaliação não encontrada ou já desativada.");
        }

        validarCamposUpdate(request);

        AvaliacaoProfessor avaliacao = avaliacaoOpt.get();

        if (request.getDidatica() != null) {
            avaliacao.setDidatica(request.getDidatica());
        }
        if (request.getQualidadeAula() != null) {
            avaliacao.setQualidadeAula(request.getQualidadeAula());
        }
        if (request.getFlexibilidade() != null) {
            avaliacao.setFlexibilidade(request.getFlexibilidade());
        }
        if (request.getOrganizacao() != null) {
            avaliacao.setOrganizacao(request.getOrganizacao());
        }
        if (request.getComentario() != null) {
            avaliacao.setComentario(request.getComentario());
        }

        repository.save(avaliacao);
        System.out.println("Avaliação ID " + id + " atualizada com sucesso.");
    }

    private void validarCamposUpdate(AvaliacaoProfessorUpdateDTO request) {
        if (request.getDidatica() != null && (request.getDidatica() < 1 || request.getDidatica() > 5)) {
            throw new AvaliacaoInvalidaException("Nota de didática deve ser entre 1 e 5.");
        }
        if (request.getQualidadeAula() != null && (request.getQualidadeAula() < 1 || request.getQualidadeAula() > 5)) {
            throw new AvaliacaoInvalidaException("Nota de qualidade da aula deve ser entre 1 e 5.");
        }
        if (request.getFlexibilidade() != null && (request.getFlexibilidade() < 1 || request.getFlexibilidade() > 5)) {
            throw new AvaliacaoInvalidaException("Nota de flexibilidade deve ser entre 1 e 5.");
        }
        if (request.getOrganizacao() != null && (request.getOrganizacao() < 1 || request.getOrganizacao() > 5)) {
            throw new AvaliacaoInvalidaException("Nota de organização deve ser entre 1 e 5.");
        }
    }
}