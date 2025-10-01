-- V10: Fix user permissions for danielakiyama8@gmail.com

-- Delete existing permissions for this user if any
DELETE FROM mt_usuario_permissao_java 
WHERE usuario_id IN (
    SELECT id_usuario FROM mt_usuario_java 
    WHERE email_usuario = 'danielakiyama8@gmail.com'
);

-- If the user doesn't exist, create them with ADMIN permissions
INSERT INTO mt_usuario_java (nome_usuario, cpf_usuario, email_usuario, senha_usuario, cnh_usuario, data_nascimento_usuario, criado_em_usuario)
SELECT 'Daniel Akiyama', '12345678901', 'danielakiyama8@gmail.com', '$2a$10$pNOQ7MaZzScDv9p2V21FPOAXClQ5kT42QvHfbSOmtEM0w.ixOxAaa', '123456789', SYSDATE, SYSDATE
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM mt_usuario_java WHERE email_usuario = 'danielakiyama8@gmail.com'
);

-- Assign ADMIN permission to danielakiyama8@gmail.com
INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'ADMIN'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'danielakiyama8@gmail.com' AND p.nome_permissao = 'ADMIN';
