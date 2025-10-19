-- ALTERAR COLUNAS DE CHAVE ESTRANGEIRA PARA BIGINT
ALTER TABLE avaliacao_professor
    ALTER COLUMN professor_id TYPE BIGINT;

ALTER TABLE avaliacao_coordenador
    ALTER COLUMN coordenador_id TYPE BIGINT;

ALTER TABLE avaliacao_disciplina
    ALTER COLUMN disciplina_id TYPE BIGINT;
