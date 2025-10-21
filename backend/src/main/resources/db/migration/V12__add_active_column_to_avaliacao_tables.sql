-- Migration: V12__add_active_column_to_avaliacao_tables.sql
-- Descrição: Adiciona coluna active às tabelas caso não tenha

-- Adiciona coluna active à tabela avaliacao_coordenador (se não existir)
ALTER TABLE avaliacao_coordenador
ADD COLUMN IF NOT EXISTS active BOOLEAN NOT NULL DEFAULT TRUE;

-- Adiciona coluna active à tabela avaliacao_disciplina (se não existir)
ALTER TABLE avaliacao_disciplina
ADD COLUMN IF NOT EXISTS active BOOLEAN NOT NULL DEFAULT TRUE;

-- Adiciona coluna active à tabela avaliacao_professor (se não existir)
ALTER TABLE avaliacao_professor
ADD COLUMN IF NOT EXISTS active BOOLEAN DEFAULT TRUE;