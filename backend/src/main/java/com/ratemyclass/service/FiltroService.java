package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoResponseDTO;
import com.ratemyclass.entity.enums.TipoAvaliacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FiltroService {

    private final FeedAvaliacoesService feedAvaliacoesService;

    public List<AvaliacaoResponseDTO> filtrarAvaliacoes(TipoAvaliacao tipo, String orderBy, String order) {
        List<AvaliacaoResponseDTO> avaliacoes = feedAvaliacoesService.listarAvaliacoes();

        if (tipo != null) {
            avaliacoes = avaliacoes.stream()
                    .filter(a -> a.getTipoAvaliacao() == tipo)
                    .collect(Collectors.toList());
        }

        if (orderBy != null) {
            avaliacoes = ordenarAvaliacoes(avaliacoes, orderBy, order);
        }

        return avaliacoes;
    }

    public List<AvaliacaoResponseDTO> filtrarPorTipo(TipoAvaliacao tipo) {
        return feedAvaliacoesService.listarAvaliacoes().stream()
                .filter(a -> a.getTipoAvaliacao() == tipo)
                .collect(Collectors.toList());
    }

    public List<AvaliacaoResponseDTO> listarAvaliacoesRecentes(Integer limit) {
        return feedAvaliacoesService.listarAvaliacoes().stream()
                .sorted(Comparator.comparing(AvaliacaoResponseDTO::getAvaliacaoId).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<AvaliacaoResponseDTO> listarAvaliacoesPopulares(Integer limit) {
        return feedAvaliacoesService.listarAvaliacoes().stream()
                .sorted((a, b) -> {
                    long totalA = (a.getLikes() != null ? a.getLikes() : 0) - 
                                  (a.getDeslikes() != null ? a.getDeslikes() : 0);
                    long totalB = (b.getLikes() != null ? b.getLikes() : 0) - 
                                  (b.getDeslikes() != null ? b.getDeslikes() : 0);
                    return Long.compare(totalB, totalA);
                })
                .limit(limit)
                .collect(Collectors.toList());
    }

    private List<AvaliacaoResponseDTO> ordenarAvaliacoes(List<AvaliacaoResponseDTO> avaliacoes, 
                                                         String orderBy, String order) {
        Comparator<AvaliacaoResponseDTO> comparator;

        switch (orderBy.toLowerCase()) {
            case "likes":
                comparator = Comparator.comparing(a -> a.getLikes() != null ? a.getLikes() : 0);
                break;
            case "deslikes":
                comparator = Comparator.comparing(a -> a.getDeslikes() != null ? a.getDeslikes() : 0);
                break;
            case "popularidade":
                comparator = Comparator.comparing(a -> 
                    (a.getLikes() != null ? a.getLikes() : 0L) - 
                    (a.getDeslikes() != null ? a.getDeslikes() : 0L)
                );
                break;
            case "id":
            default:
                comparator = Comparator.comparing(AvaliacaoResponseDTO::getAvaliacaoId);
                break;
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return avaliacoes.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
