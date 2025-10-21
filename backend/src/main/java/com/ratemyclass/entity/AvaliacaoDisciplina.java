package com.ratemyclass.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "avaliacao_disciplina")
public class AvaliacaoDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "disciplina_id", nullable = false)
    private Long disciplinaId;

    @Column(nullable = false)
    private Integer dificuldade;

    @Column(name = "metodologia", nullable = false)
    private Integer metodologia;

    @Column(nullable = false)
    private Integer conteudos;

    @Column(nullable = false)
    private Integer aplicabilidade;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(nullable = false)
    private String visibilidade;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
