-- Migration: V16__add_usuario_id_to_avaliacoes
-- Descrição: Adiciona coluna usuario_id nas 3 tabelas de avaliação

-- === avaliacao_professor ===
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'avaliacao_professor' AND column_name = 'usuario_id'
    ) THEN
        ALTER TABLE avaliacao_professor ADD COLUMN usuario_id BIGINT;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'fk_avaliacao_professor_usuario'
    ) THEN
        ALTER TABLE avaliacao_professor
            ADD CONSTRAINT fk_avaliacao_professor_usuario
            FOREIGN KEY (usuario_id) REFERENCES users(id);
    END IF;
END $$;


-- === avaliacao_disciplina ===
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'avaliacao_disciplina' AND column_name = 'usuario_id'
    ) THEN
        ALTER TABLE avaliacao_disciplina ADD COLUMN usuario_id BIGINT;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'fk_avaliacao_disciplina_usuario'
    ) THEN
        ALTER TABLE avaliacao_disciplina
            ADD CONSTRAINT fk_avaliacao_disciplina_usuario
            FOREIGN KEY (usuario_id) REFERENCES users(id);
    END IF;
END $$;


-- === avaliacao_coordenador ===
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'avaliacao_coordenador' AND column_name = 'usuario_id'
    ) THEN
        ALTER TABLE avaliacao_coordenador ADD COLUMN usuario_id BIGINT;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'fk_avaliacao_coordenador_usuario'
    ) THEN
        ALTER TABLE avaliacao_coordenador
            ADD CONSTRAINT fk_avaliacao_coordenador_usuario
            FOREIGN KEY (usuario_id) REFERENCES users(id);
    END IF;
END $$;
