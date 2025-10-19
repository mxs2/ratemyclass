-- V9__insert_sample_data_avaliacoes.sql
-- Inserindo uma avaliação para cada categoria


-- Avaliação de Professor
INSERT INTO avaliacao_professor
(professor_id, didatica, qualidade_aula, flexibilidade, organizacao, comentario, visibilidade, active)
VALUES
(1, 9, 10, 8, 9, 'Professor muito didático, explica com clareza e sempre disposto a ajudar.', 'publica', TRUE);

-- Avaliação de Coordenador
INSERT INTO avaliacao_coordenador
(coordenador_id, transparencia, interacao_aluno, suporte, organizacao, comentario, visibilidade, active)
VALUES
(2, 8, 9, 9, 8, 'Coordenador acessível e transparente, responde rapidamente às dúvidas.', 'publica', TRUE);

-- Avaliação de Disciplina

INSERT INTO avaliacao_disciplina
(disciplina_id, dificuldade, metodologia, conteudos, aplicabilidade, comentario, visibilidade, active)
VALUES
(2, 9, 8, 9, 9, 'Difícil, mas essencial. Exige bastante prática para compreender as estruturas.', 'publica', TRUE);
