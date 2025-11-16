package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoResponseDTO;
import com.ratemyclass.entity.enums.TipoAvaliacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FiltroServiceTest {

    @Mock
    private FeedAvaliacoesService feedAvaliacoesService;

    @InjectMocks
    private FiltroService filtroService;

    private List<AvaliacaoResponseDTO> mockAvaliacoes;

    @BeforeEach
    void setUp() {
        AvaliacaoResponseDTO avaliacaoProfessor = AvaliacaoResponseDTO.builder()
                .avaliacaoId(1L)
                .tipoAvaliacao(TipoAvaliacao.PROFESSOR)
                .nomeReferencia("João Victor Tinoco")
                .comentario("Excelente professor")
                .likes(10L)
                .deslikes(2L)
                .didatica(8)
                .qualidadeAula(9)
                .flexibilidade(7)
                .organizacaoProfessor(8)
                .build();

        AvaliacaoResponseDTO avaliacaoDisciplina = AvaliacaoResponseDTO.builder()
                .avaliacaoId(2L)
                .tipoAvaliacao(TipoAvaliacao.DISCIPLINA)
                .nomeReferencia("Programação Orientada a Objeto")
                .comentario("Ótima disciplina")
                .likes(15L)
                .deslikes(1L)
                .dificuldade(7)
                .metodologia(8)
                .conteudos(9)
                .aplicabilidade(8)
                .build();

        AvaliacaoResponseDTO avaliacaoCoordenador = AvaliacaoResponseDTO.builder()
                .avaliacaoId(3L)
                .tipoAvaliacao(TipoAvaliacao.COORDENADOR)
                .nomeReferencia("Carla Alexandre")
                .comentario("Coordenador atencioso")
                .likes(5L)
                .deslikes(3L)
                .transparencia(9)
                .interacaoAluno(8)
                .suporte(7)
                .organizacaoCoordenador(8)
                .build();

        mockAvaliacoes = Arrays.asList(avaliacaoProfessor, avaliacaoDisciplina, avaliacaoCoordenador);
    }

    @Test
    void testFiltrarPorTipo_Professor() {
        when(feedAvaliacoesService.listarAvaliacoes()).thenReturn(mockAvaliacoes);

        List<AvaliacaoResponseDTO> result = filtroService.filtrarPorTipo(TipoAvaliacao.PROFESSOR);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TipoAvaliacao.PROFESSOR, result.get(0).getTipoAvaliacao());
        verify(feedAvaliacoesService, times(1)).listarAvaliacoes();
    }

    @Test
    void testFiltrarPorTipo_Disciplina() {
        when(feedAvaliacoesService.listarAvaliacoes()).thenReturn(mockAvaliacoes);

        List<AvaliacaoResponseDTO> result = filtroService.filtrarPorTipo(TipoAvaliacao.DISCIPLINA);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TipoAvaliacao.DISCIPLINA, result.get(0).getTipoAvaliacao());
        verify(feedAvaliacoesService, times(1)).listarAvaliacoes();
    }

    @Test
    void testListarAvaliacoesRecentes() {
        when(feedAvaliacoesService.listarAvaliacoes()).thenReturn(mockAvaliacoes);

        List<AvaliacaoResponseDTO> result = filtroService.listarAvaliacoesRecentes(5);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.get(0).getAvaliacaoId() >= result.get(1).getAvaliacaoId());
        verify(feedAvaliacoesService, times(1)).listarAvaliacoes();
    }

    @Test
    void testListarAvaliacoesPopulares() {
        when(feedAvaliacoesService.listarAvaliacoes()).thenReturn(mockAvaliacoes);

        List<AvaliacaoResponseDTO> result = filtroService.listarAvaliacoesPopulares(5);

        assertNotNull(result);
        assertEquals(3, result.size());
        long firstPopularity = (result.get(0).getLikes() - result.get(0).getDeslikes());
        long secondPopularity = (result.get(1).getLikes() - result.get(1).getDeslikes());
        assertTrue(firstPopularity >= secondPopularity);
        verify(feedAvaliacoesService, times(1)).listarAvaliacoes();
    }

    @Test
    void testFiltrarAvaliacoes_ComTipo() {
        when(feedAvaliacoesService.listarAvaliacoes()).thenReturn(mockAvaliacoes);

        List<AvaliacaoResponseDTO> result = filtroService.filtrarAvaliacoes(
                TipoAvaliacao.PROFESSOR, null, "desc");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TipoAvaliacao.PROFESSOR, result.get(0).getTipoAvaliacao());
        verify(feedAvaliacoesService, times(1)).listarAvaliacoes();
    }

    @Test
    void testFiltrarAvaliacoes_ComOrdenacao() {
        when(feedAvaliacoesService.listarAvaliacoes()).thenReturn(mockAvaliacoes);

        List<AvaliacaoResponseDTO> result = filtroService.filtrarAvaliacoes(
                null, "likes", "desc");

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.get(0).getLikes() >= result.get(1).getLikes());
        verify(feedAvaliacoesService, times(1)).listarAvaliacoes();
    }

    @Test
    void testFiltrarAvaliacoes_SemFiltros() {
        when(feedAvaliacoesService.listarAvaliacoes()).thenReturn(mockAvaliacoes);

        List<AvaliacaoResponseDTO> result = filtroService.filtrarAvaliacoes(null, null, null);

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(feedAvaliacoesService, times(1)).listarAvaliacoes();
    }

    @Test
    void testListarAvaliacoesRecentes_ComLimite() {
        when(feedAvaliacoesService.listarAvaliacoes()).thenReturn(mockAvaliacoes);

        List<AvaliacaoResponseDTO> result = filtroService.listarAvaliacoesRecentes(2);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(feedAvaliacoesService, times(1)).listarAvaliacoes();
    }

    @Test
    void testListarAvaliacoesPopulares_Ordenacao() {
        when(feedAvaliacoesService.listarAvaliacoes()).thenReturn(mockAvaliacoes);

        List<AvaliacaoResponseDTO> result = filtroService.listarAvaliacoesPopulares(10);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(2L, result.get(0).getAvaliacaoId());
        assertEquals(1L, result.get(1).getAvaliacaoId());
        assertEquals(3L, result.get(2).getAvaliacaoId());
        verify(feedAvaliacoesService, times(1)).listarAvaliacoes();
    }
}
