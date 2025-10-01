-- V11: Debug and fix user permissions
-- First, let's check what's in the database

-- Check if user exists
SELECT 'User exists:' as info, COUNT(*) as count FROM mt_usuario_java WHERE email_usuario = 'danielakiyama8@gmail.com';

-- Check if permissions exist
SELECT 'Permissions exist:' as info, COUNT(*) as count FROM mt_permissao_java;

-- Check if user has any permissions
SELECT 'User permissions:' as info, COUNT(*) as count FROM mt_usuario_permissao_java up
JOIN mt_usuario_java u ON u.id_usuario = up.usuario_id
WHERE u.email_usuario = 'danielakiyama8@gmail.com';

-- Show all user permissions
SELECT 'User permission details:' as info, u.email_usuario, up.papel, p.nome_permissao
FROM mt_usuario_java u
LEFT JOIN mt_usuario_permissao_java up ON u.id_usuario = up.usuario_id
LEFT JOIN mt_permissao_java p ON up.permissao_id = p.id_permissao
WHERE u.email_usuario = 'danielakiyama8@gmail.com';

-- Force create the user if not exists
INSERT INTO mt_usuario_java (nome_usuario, cpf_usuario, email_usuario, senha_usuario, cnh_usuario, data_nascimento_usuario, criado_em_usuario)
SELECT 'Daniel Akiyama', '12345678901', 'danielakiyama8@gmail.com', '$2a$10$pNOQ7MaZzScDv9p2V21FPOAXClQ5kT42QvHfbSOmtEM0w.ixOxAaa', '123456789', SYSDATE, SYSDATE
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM mt_usuario_java WHERE email_usuario = 'danielakiyama8@gmail.com');

-- Force assign ADMIN permission
INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'ADMIN'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'danielakiyama8@gmail.com' 
AND p.nome_permissao = 'ADMIN'
AND NOT EXISTS (
    SELECT 1 FROM mt_usuario_permissao_java up2 
    WHERE up2.usuario_id = u.id_usuario 
    AND up2.papel = 'ADMIN'
);

-- Final check
SELECT 'Final check:' as info, u.email_usuario, up.papel, p.nome_permissao
FROM mt_usuario_java u
LEFT JOIN mt_usuario_permissao_java up ON u.id_usuario = up.usuario_id
LEFT JOIN mt_permissao_java p ON up.permissao_id = p.id_permissao
WHERE u.email_usuario = 'danielakiyama8@gmail.com';
