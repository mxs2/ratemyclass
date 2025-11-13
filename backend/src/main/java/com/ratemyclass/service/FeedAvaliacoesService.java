package com.ratemyclass.service;

import com.ratemyclass.dto.avaliacao.AvaliacaoResponseDTO;
import com.ratemyclass.entity.*;
import com.ratemyclass.entity.enums.TipoAvaliacao;
import com.ratemyclass.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedAvaliacoesService {

        private final AvaliacaoProfessorRepository avaliacoesProfessorRepo;
        private final AvaliacaoDisciplinaRepository avaliacoesDisciplinaRepo;
        private final AvaliacaoCoordenadorRepository avaliacoesCoordenadorRepo;
        private final AvaliacaoReacaoRepository reacaoRepo;

        private final ProfessorRepository professorRepository;
        private final DisciplinaRepository disciplianRepository;
        private final CoordenadorRepository coordenadorRepository;

        @Autowired
        private UserService userService;

        public List<AvaliacaoResponseDTO> listarAvaliacoes() {
                List<AvaliacaoResponseDTO> result = new ArrayList<>();

                User usuario = userService.getUsuarioAutenticado();

                avaliacoesProfessorRepo.findAllByActiveTrue().forEach(a -> {
                        var professorNome = professorRepository.findById(a.getProfessorId())
                                        .map(Professor::getNome)
                                        .orElse("Professor não encontrado");

                        var userReaction = reacaoRepo
                                        .findByUsuarioIdAndAvaliacaoIdAndTipoAvaliacao(usuario.getId(), a.getId(),
                                                        TipoAvaliacao.PROFESSOR)
                                        .map(AvaliacaoReacao::getTipoReacao)
                                        .orElse(null);

                        result.add(
                                        AvaliacaoResponseDTO.builder()
                                                        .avaliacaoId(a.getId())
                                                        .tipoAvaliacao(TipoAvaliacao.PROFESSOR)
                                                        .nomeReferencia(professorNome)
                                                        .comentario(a.getComentario())
                                                        .likes(reacaoRepo.countLikes(a.getId(),
                                                                        TipoAvaliacao.PROFESSOR))
                                                        .deslikes(reacaoRepo.countDeslikes(a.getId(),
                                                                        TipoAvaliacao.PROFESSOR))
                                                        .didatica(a.getDidatica())
                                                        .qualidadeAula(a.getQualidadeAula())
                                                        .flexibilidade(a.getFlexibilidade())
                                                        .organizacaoProfessor(a.getOrganizacao())
                                                        .userReaction(userReaction)
                                                        .build());
                });

                avaliacoesDisciplinaRepo.findAllByActiveTrue().forEach(a -> {
                        var disciplinaNome = disciplianRepository.findById(a.getDisciplinaId())
                                        .map(Disciplina::getNome)
                                        .orElse("Disciplina não encontrada");

                        var userReaction = reacaoRepo
                                        .findByUsuarioIdAndAvaliacaoIdAndTipoAvaliacao(usuario.getId(), a.getId(),
                                                        TipoAvaliacao.DISCIPLINA)
                                        .map(AvaliacaoReacao::getTipoReacao)
                                        .orElse(null);

                        result.add(
                                        AvaliacaoResponseDTO.builder()
                                                        .avaliacaoId(a.getId())
                                                        .tipoAvaliacao(TipoAvaliacao.DISCIPLINA)
                                                        .nomeReferencia(disciplinaNome)
                                                        .comentario(a.getComentario())
                                                        .likes(reacaoRepo.countLikes(a.getId(),
                                                                        TipoAvaliacao.DISCIPLINA))
                                                        .deslikes(reacaoRepo.countDeslikes(a.getId(),
                                                                        TipoAvaliacao.DISCIPLINA))
                                                        .dificuldade(a.getDificuldade())
                                                        .metodologia(a.getMetodologia())
                                                        .conteudos(a.getConteudos())
                                                        .aplicabilidade(a.getAplicabilidade())
                                                        .userReaction(userReaction)
                                                        .build());
                });

                avaliacoesCoordenadorRepo.findAllByActiveTrue().forEach(a -> {
                        var coordenadorNome = coordenadorRepository.findById(a.getCoordenadorId())
                                        .map(Coordenador::getNome)
                                        .orElse("Coordenador não encontrado");

                        var userReaction = reacaoRepo
                                        .findByUsuarioIdAndAvaliacaoIdAndTipoAvaliacao(usuario.getId(), a.getId(),
                                                        TipoAvaliacao.COORDENADOR)
                                        .map(AvaliacaoReacao::getTipoReacao)
                                        .orElse(null);

                        result.add(
                                        AvaliacaoResponseDTO.builder()
                                                        .avaliacaoId(a.getId())
                                                        .tipoAvaliacao(TipoAvaliacao.COORDENADOR)
                                                        .nomeReferencia(coordenadorNome)
                                                        .comentario(a.getComentario())
                                                        .likes(reacaoRepo.countLikes(a.getId(),
                                                                        TipoAvaliacao.COORDENADOR))
                                                        .deslikes(reacaoRepo.countDeslikes(a.getId(),
                                                                        TipoAvaliacao.COORDENADOR))
                                                        .transparencia(a.getTransparencia())
                                                        .interacaoAluno(a.getInteracaoAluno())
                                                        .suporte(a.getSuporte())
                                                        .organizacaoCoordenador(a.getOrganizacao())
                                                        .userReaction(userReaction)
                                                        .build());
                });

                return result;
        }
}