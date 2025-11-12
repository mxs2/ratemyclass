package com.ratemyclass.service;

import com.ratemyclass.entity.AvaliacaoReacao;
import com.ratemyclass.entity.enums.TipoAvaliacao;
import com.ratemyclass.entity.enums.TipoReacao;
import com.ratemyclass.repository.AvaliacaoReacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliacaoReacaoService {

    private final AvaliacaoReacaoRepository repository;

    @Autowired
    private UserService userService;

    public void toggleReacao(Long avaliacaoId, TipoAvaliacao tipoAvaliacao, TipoReacao tipoReacao) {
        var usuarioId = userService.getUsuarioAutenticado().getId();

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