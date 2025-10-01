-- V6: Fix user passwords with proper BCrypt hashes for "123456"
-- All users will have the password: "123456"
-- Update admin user password
UPDATE mt_usuario_java 
SET senha_usuario = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi' 
WHERE email_usuario = 'admin@mottu.com';

-- Update manager user password  
UPDATE mt_usuario_java 
SET senha_usuario = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi' 
WHERE email_usuario = 'gerente@mottu.com';

-- Update operator user password
UPDATE mt_usuario_java 
SET senha_usuario = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi' 
WHERE email_usuario = 'operador@mottu.com';

-- Update regular user password
UPDATE mt_usuario_java 
SET senha_usuario = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi' 
WHERE email_usuario = 'usuario@mottu.com';
