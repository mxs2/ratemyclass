package com.ratemyclass.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratemyclass.dto.reacao.AvaliacaoReacaoRequest;
import com.ratemyclass.entity.enums.TipoAvaliacao;
import com.ratemyclass.entity.enums.TipoReacao;
import com.ratemyclass.service.AvaliacaoReacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Foca só no controller
@WebMvcTest(controllers = AvaliacaoReacaoController.class)
@AutoConfigureMockMvc(addFilters = false) // desativa filtros de segurança
class AvaliacaoReacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvaliacaoReacaoService service;

    // 🔥 adiciona mocks falsos para evitar erro de dependência de segurança
    @MockBean
    private com.ratemyclass.service.JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveChamarServicoComParametrosCorretosERetornarOk() throws Exception {
        // Arrange
        AvaliacaoReacaoRequest request = new AvaliacaoReacaoRequest(
                10L,
                TipoAvaliacao.PROFESSOR,
                TipoReacao.LIKE
        );

        // Act + Assert
        mockMvc.perform(post("/reacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Assert
        verify(service).toggleReacao(10L, TipoAvaliacao.PROFESSOR, TipoReacao.LIKE);
    }
}