-- V12: Create permissions and assign to user
-- First, ensure permissions exist
INSERT INTO mt_permissao_java (nome_permissao, descricao) 
SELECT 'ADMIN', 'Administrador do sistema' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mt_permissao_java WHERE nome_permissao = 'ADMIN');

INSERT INTO mt_permissao_java (nome_permissao, descricao) 
SELECT 'GERENTE', 'Gerente de operações' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mt_permissao_java WHERE nome_permissao = 'GERENTE');

INSERT INTO mt_permissao_java (nome_permissao, descricao) 
SELECT 'OPERADOR', 'Operador de pátio' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mt_permissao_java WHERE nome_permissao = 'OPERADOR');

INSERT INTO mt_permissao_java (nome_permissao, descricao) 
SELECT 'USER', 'Usuário comum' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mt_permissao_java WHERE nome_permissao = 'USER');

-- Now assign ADMIN permission to danielakiyama8@gmail.com
INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'ADMIN'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'danielakiyama8@gmail.com' 
AND p.nome_permissao = 'ADMIN'
AND NOT EXISTS (
    SELECT 1 FROM mt_usuario_permissao_java up 
    WHERE up.usuario_id = u.id_usuario 
    AND up.papel = 'ADMIN'
);

-- Also assign ADMIN permission to admin@mottu.com if it exists
INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'ADMIN'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'admin@mottu.com' 
AND p.nome_permissao = 'ADMIN'
AND NOT EXISTS (
    SELECT 1 FROM mt_usuario_permissao_java up 
    WHERE up.usuario_id = u.id_usuario 
    AND up.papel = 'ADMIN'
);
