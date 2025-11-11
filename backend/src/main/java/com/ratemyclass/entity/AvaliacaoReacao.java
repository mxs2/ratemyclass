package com.ratemyclass.entity;

import com.ratemyclass.entity.enums.TipoAvaliacao;
import com.ratemyclass.entity.enums.TipoReacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao_reacao",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "idx_unico_reacao_usuario_avaliacao",
                        columnNames = {"usuario_id", "avaliacao_id", "tipo_avaliacao"}
                )
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliacaoReacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_avaliacao", nullable = false)
    private TipoAvaliacao tipoAvaliacao;

    @Column(name = "avaliacao_id", nullable = false)
    private Long avaliacaoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_reacao", nullable = false)
    private TipoReacao tipoReacao;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}