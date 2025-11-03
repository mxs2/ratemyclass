
-- Migration: V14__alter_id_columns_to_bigint.sql
-- Descrição: Corrige o tipo da coluna 'id' para BIGINT nas tabelas principais

-- Professores
ALTER TABLE professores
ALTER COLUMN id TYPE BIGINT;

-- Disciplinas
ALTER TABLE disciplinas
ALTER COLUMN id TYPE BIGINT;

-- Coordenadores
ALTER TABLE coordenadores
ALTER COLUMN id TYPE BIGINT;

-- Comentário: essa mudança é necessária porque o código Java
-- usa 'Long' como tipo para o campo ID, que corresponde a BIGINT no PostgreSQL.
