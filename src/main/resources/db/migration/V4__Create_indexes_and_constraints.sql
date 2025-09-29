-- V4: Create indexes and constraints
-- Create indexes for better performance
CREATE INDEX idx_usuario_email ON mt_usuario_java(email_usuario);
CREATE INDEX idx_usuario_cpf ON mt_usuario_java(cpf_usuario);
CREATE INDEX idx_moto_placa ON mt_moto_java(placa_moto);
CREATE INDEX idx_moto_estado ON mt_moto_java(estado_moto);
CREATE INDEX idx_contrato_usuario ON mt_contrato_java(usuario_contrato_id);
CREATE INDEX idx_contrato_moto ON mt_contrato_java(moto_contrato_id);
CREATE INDEX idx_contrato_ativo ON mt_contrato_java(ativo_contrato);

-- Add unique constraints
ALTER TABLE mt_usuario_java ADD CONSTRAINT uk_usuario_email UNIQUE (email_usuario);
ALTER TABLE mt_usuario_java ADD CONSTRAINT uk_usuario_cpf UNIQUE (cpf_usuario);
ALTER TABLE mt_moto_java ADD CONSTRAINT uk_moto_placa UNIQUE (placa_moto);
ALTER TABLE mt_moto_java ADD CONSTRAINT uk_moto_identificador UNIQUE (identificador_moto);

-- Add check constraints
ALTER TABLE mt_usuario_java ADD CONSTRAINT ck_usuario_cpf CHECK (LENGTH(cpf_usuario) = 11);
ALTER TABLE mt_moto_java ADD CONSTRAINT ck_moto_ano CHECK (ano_moto >= 2000 AND ano_moto <= EXTRACT(YEAR FROM SYSDATE) + 1);
ALTER TABLE mt_moto_java ADD CONSTRAINT ck_moto_quilometragem CHECK (quilometragem_moto >= 0);
ALTER TABLE mt_contrato_java ADD CONSTRAINT ck_contrato_valor CHECK (valor_toral_contrato > 0);
ALTER TABLE mt_contrato_java ADD CONSTRAINT ck_contrato_parcelas CHECK (quantidade_parcelas > 0);
ALTER TABLE mt_contrato_java ADD CONSTRAINT ck_contrato_renovacoes CHECK (numero_renovacoes_contrato >= 0);

-- Add foreign key constraints
ALTER TABLE mt_moto_java ADD CONSTRAINT fk_moto_contrato 
FOREIGN KEY (contrato_moto_id) REFERENCES mt_contrato_java(id_contrato);

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

