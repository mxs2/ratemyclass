-- Migration: V18__create_table_avaliacao_reacao.sql
-- Descrição: Criar tabela de reacao das avaliacoes

CREATE TABLE avaliacao_reacao (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    tipo_avaliacao VARCHAR(30) NOT NULL CHECK (
        tipo_avaliacao IN ('DISCIPLINA', 'PROFESSOR', 'COORDENADOR')
    ),
    avaliacao_id BIGINT NOT NULL,
    tipo_reacao VARCHAR(10) NOT NULL CHECK (
        tipo_reacao IN ('LIKE', 'DISLIKE')
    ),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- FK correta para tabela users
    CONSTRAINT fk_reacao_usuario
        FOREIGN KEY (usuario_id) REFERENCES users(id)
        ON DELETE CASCADE
);

-- Evita que um usuário reaja mais de uma vez a mesma avaliação
CREATE UNIQUE INDEX idx_unico_reacao_usuario_avaliacao
ON avaliacao_reacao (usuario_id, avaliacao_id, tipo_avaliacao);


