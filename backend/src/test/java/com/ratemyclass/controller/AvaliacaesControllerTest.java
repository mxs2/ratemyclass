package com.ratemyclass.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratemyclass.dto.avaliacao.AvaliacaoResponseDTO;
import com.ratemyclass.entity.enums.TipoAvaliacao;
import com.ratemyclass.service.FeedAvaliacoesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AvaliacoesController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {AvaliacoesController.class})
class AvaliacoesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedAvaliacoesService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRetornarListaDeAvaliacoesComStatusOk() throws Exception {
        var dto = AvaliacaoResponseDTO.builder()
                .avaliacaoId(1L)
                .tipoAvaliacao(TipoAvaliacao.PROFESSOR)
                .nomeReferencia("Professor Teste")
                .comentario("Muito bom")
                .likes(5L)   // corrigido para Long
                .deslikes(1L) // idem
                .build();

        when(service.listarAvaliacoes()).thenReturn(List.of(dto));

        mockMvc.perform(get("/avaliacoes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(dto))));
    }
}
