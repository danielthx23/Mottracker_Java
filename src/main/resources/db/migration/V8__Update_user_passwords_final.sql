-- V8: Update user passwords with correct BCrypt hash
-- Update all users with the correct BCrypt hash for password "123456"
UPDATE mt_usuario_java 
SET senha_usuario = '$2a$10$pNOQ7MaZzScDv9p2V21FPOAXClQ5kT42QvHfbSOmtEM0w.ixOxAaa' 
WHERE email_usuario IN ('admin@mottu.com', 'gerente@mottu.com', 'operador@mottu.com', 'usuario@mottu.com');
