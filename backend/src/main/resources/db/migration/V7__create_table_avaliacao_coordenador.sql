-- V7__create_table_avaliacao_coordenador.sql
-- Inserindo tabela de avaliação para o coordenador

CREATE TABLE avaliacao_coordenador (
    id SERIAL PRIMARY KEY,
    coordenador_id INT NOT NULL,
    transparencia INT NOT NULL CHECK (transparencia BETWEEN 0 AND 10),
    interacao_aluno INT NOT NULL CHECK (interacao_aluno BETWEEN 0 AND 10),
    suporte INT NOT NULL CHECK (suporte BETWEEN 0 AND 10),
    organizacao INT NOT NULL CHECK (organizacao BETWEEN 0 AND 10),
    comentario TEXT,
    visibilidade VARCHAR(10) NOT NULL CHECK (visibilidade IN ('publica', 'privada')),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_coordenador FOREIGN KEY (coordenador_id)
        REFERENCES coordenadores(id)
);
