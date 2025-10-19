-- V8__create_table_avaliacao_disciplina.sql
-- Inserindo tabela de avaliação para a disciplina

CREATE TABLE avaliacao_disciplina (
    id SERIAL PRIMARY KEY,
    disciplina_id INT NOT NULL,
    dificuldade INT NOT NULL CHECK (dificuldade BETWEEN 0 AND 10),
    metodologia INT NOT NULL CHECK (metodologia BETWEEN 0 AND 10),
    conteudos INT NOT NULL CHECK (conteudos BETWEEN 0 AND 10),
    aplicabilidade INT NOT NULL CHECK (aplicabilidade BETWEEN 0 AND 10),
    comentario TEXT,
    visibilidade VARCHAR(10) NOT NULL CHECK (visibilidade IN ('publica', 'privada')),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_disciplina FOREIGN KEY (disciplina_id)
        REFERENCES disciplinas(id)
)