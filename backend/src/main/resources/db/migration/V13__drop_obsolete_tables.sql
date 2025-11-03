-- Migration: V13__add_active_column_to_avaliacao_tables.sql
-- Descrição: Remoção das tabelas selecionadas (marcadas em azul no print)

DROP TABLE IF EXISTS course_prerequisites CASCADE;
DROP TABLE IF EXISTS course_professors CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS departments CASCADE;
DROP TABLE IF EXISTS disciplines CASCADE;
DROP TABLE IF EXISTS professors CASCADE;
DROP TABLE IF EXISTS ratings CASCADE;
-- Verificação opcional
-- SELECT tablename FROM pg_tables WHERE schemaname = 'public';
