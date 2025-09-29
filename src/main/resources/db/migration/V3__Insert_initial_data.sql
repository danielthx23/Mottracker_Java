-- V3: Insert initial data
-- Insert admin user
INSERT INTO mt_usuario_java (nome_usuario, cpf_usuario, email_usuario, senha_usuario, cnh_usuario, data_nascimento_usuario, criado_em_usuario) 
VALUES ('Administrador', '12345678901', 'admin@mottu.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '123456789', SYSDATE, SYSDATE);

-- Insert manager user
INSERT INTO mt_usuario_java (nome_usuario, cpf_usuario, email_usuario, senha_usuario, cnh_usuario, data_nascimento_usuario, criado_em_usuario) 
VALUES ('Gerente', '98765432109', 'gerente@mottu.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '987654321', SYSDATE, SYSDATE);

-- Insert operator user
INSERT INTO mt_usuario_java (nome_usuario, cpf_usuario, email_usuario, senha_usuario, cnh_usuario, data_nascimento_usuario, criado_em_usuario) 
VALUES ('Operador', '11122233344', 'operador@mottu.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '111222333', SYSDATE, SYSDATE);

-- Insert regular user
INSERT INTO mt_usuario_java (nome_usuario, cpf_usuario, email_usuario, senha_usuario, cnh_usuario, data_nascimento_usuario, criado_em_usuario) 
VALUES ('Usuário', '55566677788', 'usuario@mottu.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '555666777', SYSDATE, SYSDATE);

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

-- Insert sample patios
INSERT INTO mt_patio_java (nome_patio, motos_totais_patio, motos_disponiveis_patio, data_patio) 
VALUES ('Pátio Central', 50, 45, SYSDATE);

INSERT INTO mt_patio_java (nome_patio, motos_totais_patio, motos_disponiveis_patio, data_patio) 
VALUES ('Pátio Norte', 30, 28, SYSDATE);

-- Insert sample motorcycles
INSERT INTO mt_moto_java (placa_moto, modelo_moto, ano_moto, quilometragem_moto, identificador_moto, estado_moto, condicoes_moto, moto_patio_atual_id) 
VALUES ('ABC1234', 'Honda CG 160', 2023, 15000, 'MOTO001', 0, 'Excelente', 1);

INSERT INTO mt_moto_java (placa_moto, modelo_moto, ano_moto, quilometragem_moto, identificador_moto, estado_moto, condicoes_moto, moto_patio_atual_id) 
VALUES ('DEF5678', 'Yamaha Fazer 250', 2022, 25000, 'MOTO002', 0, 'Boa', 1);

INSERT INTO mt_moto_java (placa_moto, modelo_moto, ano_moto, quilometragem_moto, identificador_moto, estado_moto, condicoes_moto, moto_patio_atual_id) 
VALUES ('GHI9012', 'Kawasaki Ninja 300', 2023, 8000, 'MOTO003', 0, 'Excelente', 2);

