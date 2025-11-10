package com.ratemyclass.controller;

import com.ratemyclass.dto.avaliacao.AvaliacaoCoordenadorRequestDTO;
import com.ratemyclass.dto.avaliacao.AvaliacaoCoordenadorUpdateDTO;
import com.ratemyclass.entity.AvaliacaoCoordenador;
import com.ratemyclass.exception.avaliacao.AvaliacaoInvalidaException;
import com.ratemyclass.service.AvaliacaoCoordenadorService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class AvaliacaoCoordenadorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AvaliacaoCoordenadorService service;

    @InjectMocks
    private AvaliacaoCoordenadorController controller;

    @Test
    @DisplayName("Deve cadastrar uma nova avaliação de coordenador e retornar 200 OK")
    void deveCadastrarAvaliacaoCoordenador() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        String json = """
            {
              "coordenadorId": 1,
              "transparencia": 5,
              "interacaoAluno": 4,
              "suporte": 5,
              "organizacao": 4,
              "comentario": "Excelente coordenação!",
              "visibilidade": "PÚBLICA"
            }
            """;

        doNothing().when(service).criarAvaliacao(any(AvaliacaoCoordenadorRequestDTO.class));

        mockMvc.perform(post("/avaliacoes/coordenador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Avaliação para coordenador cadastrada com sucesso!"));
    }

    @Test
    @DisplayName("Deve listar todas as avaliações e retornar 200 OK")
    void deveListarAvaliacoes() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        AvaliacaoCoordenador avaliacao = new AvaliacaoCoordenador();
        avaliacao.setCoordenadorId(1L);
        avaliacao.setTransparencia(5);
        avaliacao.setInteracaoAluno(4);
        avaliacao.setSuporte(5);
        avaliacao.setOrganizacao(4);
        avaliacao.setComentario("Muito bom!");

        when(service.listarAvaliacoes()).thenReturn(List.of(avaliacao));

        mockMvc.perform(get("/avaliacoes/coordenador"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Muito bom!"));
    }

    @Test
    @DisplayName("Deve listar avaliações de um coordenador específico e retornar 200 OK")
    void deveListarAvaliacoesPorCoordenador() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        AvaliacaoCoordenador avaliacao = new AvaliacaoCoordenador();
        avaliacao.setCoordenadorId(2L);
        avaliacao.setTransparencia(4);
        avaliacao.setInteracaoAluno(3);
        avaliacao.setSuporte(4);
        avaliacao.setOrganizacao(5);
        avaliacao.setComentario("Bom desempenho!");

        when(service.listarPorCoordenador(2L)).thenReturn(List.of(avaliacao));

        mockMvc.perform(get("/avaliacoes/coordenador/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Bom desempenho!"));
    }

    @Test
    @DisplayName("Deve deletar uma avaliação de coordenador e retornar 200 OK")
    void deveDeletarAvaliacaoCoordenador() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        doNothing().when(service).deletarAvaliacao(1L);

        mockMvc.perform(delete("/avaliacoes/coordenador/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Avaliação de coordenador removida (desativada) com sucesso!"));
    }

    @Test
    @DisplayName("Deve atualizar uma avaliação de coordenador e retornar 200 OK")
    void deveAtualizarAvaliacaoCoordenador() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        String jsonUpdate = """
            {
              "transparencia": 4,
              "interacaoAluno": 5,
              "suporte": 4,
              "organizacao": 5,
              "comentario": "Atualizado com sucesso!"
            }
            """;

        doNothing().when(service).atualizarAvaliacao(anyLong(), any(AvaliacaoCoordenadorUpdateDTO.class));

        mockMvc.perform(put("/avaliacoes/coordenador/1")
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
              "transparencia": 4
            }
            """;

        // Simulando exceção do service
        doThrow(new AvaliacaoInvalidaException("Avaliação não encontrada"))
                .when(service).atualizarAvaliacao(anyLong(), any(AvaliacaoCoordenadorUpdateDTO.class));

        mockMvc.perform(put("/avaliacoes/coordenador/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdate))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Avaliação não encontrada"));
    }
}