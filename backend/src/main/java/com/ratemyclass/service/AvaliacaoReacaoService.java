package com.ratemyclass.service;

import com.ratemyclass.entity.AvaliacaoCoordenador;
import com.ratemyclass.entity.AvaliacaoDisciplina;
import com.ratemyclass.entity.AvaliacaoProfessor;
import com.ratemyclass.entity.AvaliacaoReacao;
import com.ratemyclass.entity.enums.TipoAvaliacao;
import com.ratemyclass.entity.enums.TipoReacao;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.repository.AvaliacaoCoordenadorRepository;
import com.ratemyclass.repository.AvaliacaoDisciplinaRepository;
import com.ratemyclass.repository.AvaliacaoProfessorRepository;
import com.ratemyclass.repository.AvaliacaoReacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliacaoReacaoService {

    private final AvaliacaoReacaoRepository repository;
    private final AvaliacaoDisciplinaRepository avaliacaoDisciplinaRepository;
    private final AvaliacaoProfessorRepository avaliacaoProfessorRepository;
    private final AvaliacaoCoordenadorRepository avaliacaoCoordenadorRepository;

    @Autowired
    private UserService userService;

    public void toggleReacao(Long avaliacaoId, TipoAvaliacao tipoAvaliacao, TipoReacao tipoReacao) {
        var usuarioId = userService.getUsuarioAutenticado().getId();

        boolean isAtiva = switch (tipoAvaliacao) {
            case DISCIPLINA -> avaliacaoDisciplinaRepository.findById(avaliacaoId)
                    .map(AvaliacaoDisciplina::isActive)
                    .orElse(false);
            case PROFESSOR -> avaliacaoProfessorRepository.findById(avaliacaoId)
                    .map(AvaliacaoProfessor::isActive)
                    .orElse(false);
            case COORDENADOR -> avaliacaoCoordenadorRepository.findById(avaliacaoId)
                    .map(AvaliacaoCoordenador::isActive)
                    .orElse(false);
        };

        if (!isAtiva) {
            throw new AvaliacaoInvalidaException("Não é possível reagir a uma avaliação inativa.");
        }

        var existente = repository.findByUsuarioIdAndAvaliacaoIdAndTipoAvaliacao(
                usuarioId, avaliacaoId, tipoAvaliacao
        );

        if (existente.isPresent()) {
            var reacao = existente.get();

            // Se já é o mesmo tipo → remove (toggle)
            if (reacao.getTipoReacao() == tipoReacao) {
                repository.delete(reacao);
                return;
            }

            // Se é diferente → troca
            reacao.setTipoReacao(tipoReacao);
            repository.save(reacao);
            return;
        }

        // Se não existe → cria nova
        AvaliacaoReacao nova = AvaliacaoReacao.builder()
                .usuarioId(usuarioId)
                .avaliacaoId(avaliacaoId)
                .tipoAvaliacao(tipoAvaliacao)
                .tipoReacao(tipoReacao)
                .build();

        repository.save(nova);
    }
}