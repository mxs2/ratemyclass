-- V17__update_demo_user_credentials.sql
-- Descrição: Atualiza credenciais do usuário demo

-- Atualiza email e senha do usuário demo
UPDATE users
SET
    email = 'demo@cesar.school',
    password = '$2a$12$EQbvGoa.fTIv2qkrWZi/EepzELsRedvkSeL4Ws4ZqJ18cau9yEsuS', -- BCrypt de "cesar123"
    updated_at = CURRENT_TIMESTAMP
WHERE email IN ('demo@university.edu', 'demo@cesar.school');

-- Atualiza avaliações existentes com NULL para o id do demo

-- Tabela avaliacao_coordenador
UPDATE avaliacao_coordenador
SET usuario_id = 9
WHERE usuario_id IS NULL;

-- Tabela avaliacao_disciplina
UPDATE avaliacao_disciplina
SET usuario_id = 9
WHERE usuario_id IS NULL;

-- Tabela avaliacao_professor
UPDATE avaliacao_professor
SET usuario_id = 9
WHERE usuario_id IS NULL;