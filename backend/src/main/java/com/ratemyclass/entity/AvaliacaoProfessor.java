package com.ratemyclass.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "avaliacao_professor")
public class AvaliacaoProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "professor_id", nullable = false)
    private Long professorId;

    @Column(nullable = false)
    private Integer didatica;

    @Column(name = "qualidade_aula", nullable = false)
    private Integer qualidadeAula;

    @Column(nullable = false)
    private Integer flexibilidade;

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
