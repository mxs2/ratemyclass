package com.ratemyclass.controller;

import com.ratemyclass.dto.avaliacao.AvaliacaoDisciplinaRequestDTO;
import com.ratemyclass.dto.avaliacao.AvaliacaoDisciplinaUpdateDTO;
import com.ratemyclass.entity.AvaliacaoDisciplina;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.service.AvaliacaoDisciplinaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class AvaliacaoDisciplinaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AvaliacaoDisciplinaService service;

    @InjectMocks
    private AvaliacaoDisciplinaController controller;

    @Test
    @DisplayName("Deve cadastrar uma nova avaliação de disciplina e retornar 200 OK")
    void deveCadastrarAvaliacaoDisciplina() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        String json = """
                {
                  "disciplinaId": 1,
                  "dificuldade": 8,
                  "metodologia": 9,
                  "conteudos": 7,
                  "aplicabilidade": 10,
                  "comentario": "Excelente disciplina!",
                  "visibilidade": "PÚBLICA"
                }
                """;

        doNothing().when(service).criarAvaliacao(any(AvaliacaoDisciplinaRequestDTO.class));

        mockMvc.perform(post("/avaliacoes/disciplina")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Avaliação para disciplina cadastrada com sucesso!"));
    }

    @Test
    @DisplayName("Deve listar todas as avaliações de disciplina e retornar 200 OK")
    void deveListarAvaliacoes() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        AvaliacaoDisciplina avaliacao = new AvaliacaoDisciplina();
        avaliacao.setDisciplinaId(1L);
        avaliacao.setDificuldade(8);
        avaliacao.setMetodologia(9);
        avaliacao.setConteudos(7);
        avaliacao.setAplicabilidade(10);
        avaliacao.setComentario("Boa disciplina!");

        when(service.listarAvaliacoes()).thenReturn(List.of(avaliacao));

        mockMvc.perform(get("/avaliacoes/disciplina"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Boa disciplina!"));
    }

    @Test
    @DisplayName("Deve buscar uma avaliação de disciplina por ID e retornar 200 OK")
    void deveBuscarAvaliacaoPorId() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        AvaliacaoDisciplina avaliacao = new AvaliacaoDisciplina();
        avaliacao.setDisciplinaId(1L);
        avaliacao.setDificuldade(8);
        avaliacao.setMetodologia(9);
        avaliacao.setConteudos(7);
        avaliacao.setAplicabilidade(10);
        avaliacao.setComentario("Disciplina específica");

        when(service.buscarPorId(1L)).thenReturn(avaliacao);

        mockMvc.perform(get("/avaliacoes/disciplina/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario").value("Disciplina específica"))
                .andExpect(jsonPath("$.dificuldade").value(8));
    }

    @Test
    @DisplayName("Deve deletar uma avaliação de disciplina e retornar 200 OK")
    void deveDeletarAvaliacaoDisciplina() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        doNothing().when(service).deletarAvaliacao(1L);

        mockMvc.perform(delete("/avaliacoes/disciplina/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Avaliação de disciplina removida (desativada) com sucesso!"));
    }

    @Test
    @DisplayName("Deve atualizar uma avaliação de disciplina e retornar 200 OK")
    void deveAtualizarAvaliacaoDisciplina() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        String jsonUpdate = """
                {
                  "dificuldade": 6,
                  "metodologia": 8,
                  "conteudos": 7,
                  "aplicabilidade": 9,
                  "comentario": "Atualizado com sucesso!"
                }
                """;

        doNothing().when(service).atualizarAvaliacao(any(Long.class), any(AvaliacaoDisciplinaUpdateDTO.class));

        mockMvc.perform(put("/avaliacoes/disciplina/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdate))
                .andExpect(status().isOk())
                .andExpect(content().string("Avaliação atualizada com sucesso!"));
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request quando atualização falhar")
    void deveFalharAtualizacaoAvaliacao() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        String jsonUpdate = """
                {
                  "dificuldade": 12
                }
                """;

        doThrow(new AvaliacaoInvalidaException("Avaliação não encontrada ou já desativada."))
                .when(service).atualizarAvaliacao(any(Long.class), any(AvaliacaoDisciplinaUpdateDTO.class));

        mockMvc.perform(put("/avaliacoes/disciplina/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdate))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Avaliação não encontrada ou já desativada."));
    }
}