package com.ratemyclass.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "avaliacao_coordenador")
public class AvaliacaoCoordenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coordenador_id", nullable = false)
    private Long coordenadorId;

    @Column(nullable = false)
    private Integer transparencia;

    @Column(name = "interacao_aluno", nullable = false)
    private Integer interacaoAluno;

    @Column(nullable = false)
    private Integer suporte;

    @Column(nullable = false)
    private Integer organizacao;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(nullable = false)
    private String visibilidade;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonIgnore
    private User usuario;
}