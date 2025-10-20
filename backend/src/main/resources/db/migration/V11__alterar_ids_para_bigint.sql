-- Migration: V11__alterar_ids_para_bigint.sql
-- Descrição: Ajusta o tipo das colunas ID das tabelas de avaliação para BIGINT

-- 🔹 Ajustar coluna ID da tabela avaliacao_professor
ALTER TABLE avaliacao_professor
    ALTER COLUMN id TYPE BIGINT;

-- 🔹 Ajustar coluna ID da tabela avaliacao_coordenador
ALTER TABLE avaliacao_coordenador
    ALTER COLUMN id TYPE BIGINT;

-- 🔹 Ajustar coluna ID da tabela avaliacao_disciplina
ALTER TABLE avaliacao_disciplina
    ALTER COLUMN id TYPE BIGINT;
