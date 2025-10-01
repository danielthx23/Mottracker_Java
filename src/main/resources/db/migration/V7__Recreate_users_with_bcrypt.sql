-- V7: Recreate users with proper BCrypt hashes
-- Delete existing users if they exist
DELETE FROM mt_usuario_permissao_java WHERE usuario_id IN (
    SELECT id_usuario FROM mt_usuario_java 
    WHERE email_usuario IN ('admin@mottu.com', 'gerente@mottu.com', 'operador@mottu.com', 'usuario@mottu.com')
);

DELETE FROM mt_usuario_java 
WHERE email_usuario IN ('admin@mottu.com', 'gerente@mottu.com', 'operador@mottu.com', 'usuario@mottu.com');

-- Insert users with BCrypt hashes directly
INSERT INTO mt_usuario_java (nome_usuario, cpf_usuario, email_usuario, senha_usuario, cnh_usuario, data_nascimento_usuario, criado_em_usuario)
VALUES ('Administrador', '12345678901', 'admin@mottu.com', '$2a$10$pNOQ7MaZzScDv9p2V21FPOAXClQ5kT42QvHfbSOmtEM0w.ixOxAaa', '123456789', SYSDATE, SYSDATE);

INSERT INTO mt_usuario_java (nome_usuario, cpf_usuario, email_usuario, senha_usuario, cnh_usuario, data_nascimento_usuario, criado_em_usuario)
VALUES ('Gerente', '98765432109', 'gerente@mottu.com', '$2a$10$pNOQ7MaZzScDv9p2V21FPOAXClQ5kT42QvHfbSOmtEM0w.ixOxAaa', '987654321', SYSDATE, SYSDATE);

INSERT INTO mt_usuario_java (nome_usuario, cpf_usuario, email_usuario, senha_usuario, cnh_usuario, data_nascimento_usuario, criado_em_usuario)
VALUES ('Operador', '11122233344', 'operador@mottu.com', '$2a$10$pNOQ7MaZzScDv9p2V21FPOAXClQ5kT42QvHfbSOmtEM0w.ixOxAaa', '111222333', SYSDATE, SYSDATE);

INSERT INTO mt_usuario_java (nome_usuario, cpf_usuario, email_usuario, senha_usuario, cnh_usuario, data_nascimento_usuario, criado_em_usuario)
VALUES ('Usuário', '55566677788', 'usuario@mottu.com', '$2a$10$pNOQ7MaZzScDv9p2V21FPOAXClQ5kT42QvHfbSOmtEM0w.ixOxAaa', '555666777', SYSDATE, SYSDATE);

-- Assign permissions
INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'ADMIN'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'admin@mottu.com' AND p.nome_permissao = 'ADMIN';

INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'GERENTE'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'gerente@mottu.com' AND p.nome_permissao = 'GERENTE';

INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'OPERADOR'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'operador@mottu.com' AND p.nome_permissao = 'OPERADOR';

INSERT INTO mt_usuario_permissao_java (usuario_id, permissao_id, papel)
SELECT u.id_usuario, p.id_permissao, 'USER'
FROM mt_usuario_java u, mt_permissao_java p
WHERE u.email_usuario = 'usuario@mottu.com' AND p.nome_permissao = 'USER';
