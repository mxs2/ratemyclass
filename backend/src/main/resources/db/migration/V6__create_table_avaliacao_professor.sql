-- V6__create_table_avaliacao_professor.sql
-- Inserindo tabela de avaliação para o professor

CREATE TABLE avaliacao_professor (
    id SERIAL PRIMARY KEY,
    professor_id INT NOT NULL,
    didatica INT NOT NULL CHECK (didatica BETWEEN 0 AND 10),
    qualidade_aula INT NOT NULL CHECK (qualidade_aula BETWEEN 0 AND 10),
    flexibilidade INT NOT NULL CHECK (flexibilidade BETWEEN 0 AND 10),
    organizacao INT NOT NULL CHECK (organizacao BETWEEN 0 AND 10),
    comentario TEXT,
    visibilidade VARCHAR(10) NOT NULL CHECK (visibilidade IN ('publica', 'privada')),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_professor FOREIGN KEY (professor_id)
        REFERENCES professores(id)
);
