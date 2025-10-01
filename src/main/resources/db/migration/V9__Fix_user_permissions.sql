-- V9: Fix user permissions
-- Delete existing user permissions
DELETE FROM mt_usuario_permissao_java WHERE usuario_id IN (
    SELECT id_usuario FROM mt_usuario_java 
    WHERE email_usuario IN ('admin@mottu.com', 'gerente@mottu.com', 'operador@mottu.com', 'usuario@mottu.com')
);

-- Assign permissions to admin user
INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'ADMIN'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'admin@mottu.com' AND p.nome_permissao = 'ADMIN';

-- Assign permissions to manager user
INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'GERENTE'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'gerente@mottu.com' AND p.nome_permissao = 'GERENTE';

-- Assign permissions to operator user
INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'OPERADOR'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'operador@mottu.com' AND p.nome_permissao = 'OPERADOR';

-- Assign permissions to regular user
INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'USER'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'usuario@mottu.com' AND p.nome_permissao = 'USER';
