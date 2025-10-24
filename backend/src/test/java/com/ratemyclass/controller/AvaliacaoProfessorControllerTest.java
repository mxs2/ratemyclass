package com.ratemyclass.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratemyclass.dto.avaliacao.AvaliacaoProfessorRequestDTO;
import com.ratemyclass.entity.AvaliacaoProfessor;
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
        request.setDidatica(5);
        request.setQualidadeAula(4);
        request.setFlexibilidade(4);
        request.setOrganizacao(5);
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
        avaliacao.setDidatica(5);
        avaliacao.setQualidadeAula(4);
        avaliacao.setFlexibilidade(3);
        avaliacao.setOrganizacao(4);
        avaliacao.setComentario("Bom professor!");
        avaliacao.setVisibilidade("PÚBLICA");

        when(service.listarAvaliacoes()).thenReturn(List.of(avaliacao));

        mockMvc.perform(get("/avaliacoes/professor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Bom professor!"))
                .andExpect(jsonPath("$[0].didatica").value(5));
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
}