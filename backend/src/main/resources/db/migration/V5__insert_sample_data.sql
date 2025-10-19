-- V5__insert_sample_data.sql
-- Inserindo dados iniciais nas tabelas de Professores, Coordenadores e Disciplinas

-- Professores
INSERT INTO professores (nome, active) VALUES
('João Victor Tinoco de Souza', TRUE),
('Eduardo Nascimento de Arruda', TRUE),
('José Maurício da Silva Junior', TRUE),
('Maurício da Motta Braga', TRUE);

-- Coordenadores
INSERT INTO coordenadores (nome, active) VALUES
('Carla Alexandre', TRUE),
('Diocleciano Dantas Neto', TRUE),
('Gabrielle Canalle', TRUE),
('Eduardo Ariel', TRUE),
('José Augusto Suruagy Monteiro', TRUE);

-- Disciplinas
INSERT INTO disciplinas (nome, active) VALUES
('Programação Orientada a Objeto', TRUE),
('Algoritmos e Estrutura de Dados', TRUE),
('Infraestrutura de Software', TRUE),
('Estatística e Probabilidade', TRUE),
('Infra de Comunicação', TRUE);