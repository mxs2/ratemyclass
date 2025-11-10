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

    // ------------------- NOVO MÉTODO DE UPDATE COM VALIDAÇÃO -------------------
    public void atualizarAvaliacao(Long id, AvaliacaoCoordenadorUpdateDTO request) {
        Optional<AvaliacaoCoordenador> avaliacaoOpt = repository.findById(id);

        if (avaliacaoOpt.isEmpty() || !avaliacaoOpt.get().isActive()) {
            throw new AvaliacaoInvalidaException("Avaliação não encontrada ou já desativada.");
        }

        AvaliacaoCoordenador avaliacao = avaliacaoOpt.get();

        // Validação simples: se o valor for nulo, não atualiza; se for inválido, lança exceção
        if (request.getTransparencia() != null && (request.getTransparencia() < 1 || request.getTransparencia() > 5)) {
            throw new AvaliacaoInvalidaException("Transparência deve ser entre 1 e 5.");
        }
        if (request.getInteracaoAluno() != null && (request.getInteracaoAluno() < 1 || request.getInteracaoAluno() > 5)) {
            throw new AvaliacaoInvalidaException("Interação com aluno deve ser entre 1 e 5.");
        }
        if (request.getSuporte() != null && (request.getSuporte() < 1 || request.getSuporte() > 5)) {
            throw new AvaliacaoInvalidaException("Suporte deve ser entre 1 e 5.");
        }
        if (request.getOrganizacao() != null && (request.getOrganizacao() < 1 || request.getOrganizacao() > 5)) {
            throw new AvaliacaoInvalidaException("Organização deve ser entre 1 e 5.");
        }

        // Atualiza apenas os campos válidos e não nulos
        if (request.getTransparencia() != null) avaliacao.setTransparencia(request.getTransparencia());
        if (request.getInteracaoAluno() != null) avaliacao.setInteracaoAluno(request.getInteracaoAluno());
        if (request.getSuporte() != null) avaliacao.setSuporte(request.getSuporte());
        if (request.getOrganizacao() != null) avaliacao.setOrganizacao(request.getOrganizacao());
        if (request.getComentario() != null) avaliacao.setComentario(request.getComentario());

        repository.save(avaliacao);

        // Log de atualização
        System.out.println("Avaliação ID " + id + " atualizada com sucesso.");
    }
}