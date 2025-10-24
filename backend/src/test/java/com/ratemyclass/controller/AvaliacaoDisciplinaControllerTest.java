package com.ratemyclass.controller;

import com.ratemyclass.dto.avaliacao.AvaliacaoDisciplinaRequestDTO;
import com.ratemyclass.entity.AvaliacaoDisciplina;
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
              "dificuldade": 4,
              "metodologia": 5,
              "conteudos": 4,
              "aplicabilidade": 5,
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
        avaliacao.setDificuldade(4);
        avaliacao.setMetodologia(5);
        avaliacao.setConteudos(4);
        avaliacao.setAplicabilidade(5);
        avaliacao.setComentario("Boa disciplina!");

        when(service.listarAvaliacoes()).thenReturn(List.of(avaliacao));

        mockMvc.perform(get("/avaliacoes/disciplina"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Boa disciplina!"));
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
}