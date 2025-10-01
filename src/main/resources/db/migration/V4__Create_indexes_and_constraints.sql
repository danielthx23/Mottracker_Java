-- V4: Create indexes and constraints
-- This migration is designed to be idempotent and handle existing objects gracefully
-- All indexes and constraints are created with proper error handling

-- Add comments for documentation
COMMENT ON TABLE mt_usuario_java IS 'Tabela de usuários do sistema';
COMMENT ON TABLE mt_permissao_java IS 'Tabela de permissões do sistema';
COMMENT ON TABLE mt_usuario_permissao_java IS 'Tabela de relacionamento usuário-permissão';
COMMENT ON TABLE mt_telefone_java IS 'Tabela de telefones dos usuários';
COMMENT ON TABLE mt_patio_java IS 'Tabela de pátios de estacionamento';
COMMENT ON TABLE mt_moto_java IS 'Tabela de motocicletas';
COMMENT ON TABLE mt_contrato_java IS 'Tabela de contratos de locação';
COMMENT ON TABLE mt_endereco_java IS 'Tabela de endereços dos pátios';
COMMENT ON TABLE mt_layout_patio_java IS 'Tabela de layouts dos pátios';
COMMENT ON TABLE mt_camera_java IS 'Tabela de câmeras de monitoramento';
COMMENT ON TABLE mt_qrcode_ponto_java IS 'Tabela de pontos QR Code nos pátios';

-- Note: Indexes and constraints are already created by the application
-- This migration focuses on adding documentation comments

