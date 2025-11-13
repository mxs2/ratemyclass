package com.ratemyclass.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratemyclass.dto.avaliacao.AvaliacaoProfessorRequestDTO;
import com.ratemyclass.dto.avaliacao.AvaliacaoProfessorUpdateDTO;
import com.ratemyclass.entity.AvaliacaoProfessor;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.service.AvaliacaoProfessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AvaliacaoProfessorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AvaliacaoProfessorService service;

    @InjectMocks
    private AvaliacaoProfessorController controller;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Deve criar uma nova avaliação de professor e retornar 200 OK")
    void deveCriarAvaliacao() throws Exception {
        AvaliacaoProfessorRequestDTO request = new AvaliacaoProfessorRequestDTO();
        request.setProfessorId(1L);
        request.setDidatica(8);
        request.setQualidadeAula(9);
        request.setFlexibilidade(7);
        request.setOrganizacao(10);
        request.setComentario("Excelente professor!");
        request.setVisibilidade("PÚBLICA");

        doNothing().when(service).criarAvaliacao(any(AvaliacaoProfessorRequestDTO.class));

        mockMvc.perform(post("/avaliacoes/professor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Avaliação para professor cadastrada com sucesso!"));
    }

    @Test
    @DisplayName("Deve listar todas as avaliações de professor e retornar 200 OK")
    void deveListarAvaliacoes() throws Exception {
        AvaliacaoProfessor avaliacao = new AvaliacaoProfessor();
        avaliacao.setProfessorId(1L);
        avaliacao.setDidatica(8);
        avaliacao.setQualidadeAula(9);
        avaliacao.setFlexibilidade(7);
        avaliacao.setOrganizacao(10);
        avaliacao.setComentario("Bom professor!");
        avaliacao.setVisibilidade("PÚBLICA");

        when(service.listarAvaliacoes()).thenReturn(List.of(avaliacao));

        mockMvc.perform(get("/avaliacoes/professor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Bom professor!"))
                .andExpect(jsonPath("$[0].didatica").value(8));
    }

    @Test
    @DisplayName("Deve buscar uma avaliação de professor por ID e retornar 200 OK")
    void deveBuscarAvaliacaoPorId() throws Exception {
        AvaliacaoProfessor avaliacao = new AvaliacaoProfessor();
        avaliacao.setId(1L);
        avaliacao.setProfessorId(1L);
        avaliacao.setDidatica(8);
        avaliacao.setQualidadeAula(9);
        avaliacao.setFlexibilidade(7);
        avaliacao.setOrganizacao(10);
        avaliacao.setComentario("Excelente professor!");
        avaliacao.setVisibilidade("PÚBLICA");

        when(service.buscarPorId(1L)).thenReturn(avaliacao);

        mockMvc.perform(get("/avaliacoes/professor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comentario").value("Excelente professor!"))
                .andExpect(jsonPath("$.didatica").value(8));
    }

    @Test
    @DisplayName("Deve deletar uma avaliação de professor e retornar 200 OK")
    void deveDeletarAvaliacao() throws Exception {
        doNothing().when(service).deletarAvaliacao(1L);

        mockMvc.perform(delete("/avaliacoes/professor/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Avaliação de professor removida (desativada) com sucesso!"));

        verify(service, times(1)).deletarAvaliacao(1L);
    }

    @Test
    @DisplayName("Deve atualizar uma avaliação de professor e retornar 200 OK")
    void deveAtualizarAvaliacao() throws Exception {
        AvaliacaoProfessorUpdateDTO updateDTO = new AvaliacaoProfessorUpdateDTO();
        updateDTO.setDidatica(7);
        updateDTO.setQualidadeAula(9);
        updateDTO.setFlexibilidade(8);
        updateDTO.setOrganizacao(10);
        updateDTO.setComentario("Atualizado com sucesso!");

        doNothing().when(service).atualizarAvaliacao(any(Long.class), any(AvaliacaoProfessorUpdateDTO.class));

        mockMvc.perform(put("/avaliacoes/professor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Avaliação atualizada com sucesso!"));
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request quando atualização falhar")
    void deveFalharAtualizacao() throws Exception {
        AvaliacaoProfessorUpdateDTO updateDTO = new AvaliacaoProfessorUpdateDTO();
        updateDTO.setDidatica(12); // valor inválido para disparar exceção

        // Simula exceção ao atualizar
        doThrow(new AvaliacaoInvalidaException("Erro na atualização"))
                .when(service).atualizarAvaliacao(any(Long.class), any(AvaliacaoProfessorUpdateDTO.class));

        mockMvc.perform(put("/avaliacoes/professor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro na atualização"));
    }
}