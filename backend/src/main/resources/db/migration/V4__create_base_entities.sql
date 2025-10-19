-- V4__create_base_entities.sql
-- Criação das tabelas de Professores, Coordenadores e Disciplinas

CREATE TABLE professores (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE coordenadores (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE disciplinas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);
