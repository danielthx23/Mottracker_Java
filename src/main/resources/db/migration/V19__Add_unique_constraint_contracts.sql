-- Adicionar constraint único para evitar duplicatas
-- Primeiro, vamos verificar se já existe algum índice único
-- SELECT * FROM USER_INDEXES WHERE TABLE_NAME = 'MT_CONTRATO_JAVA';

-- Adicionar constraint único para evitar duplicatas
ALTER TABLE MT_CONTRATO_JAVA 
ADD CONSTRAINT UK_CONTRATO_USUARIO_MOTO_DATA 
UNIQUE (USUARIO_CONTRATO_ID, MOTO_CONTRATO_ID, DATA_DE_ENTRADA_CONTRATO);

-- Verificar se a constraint foi criada
-- SELECT * FROM USER_CONSTRAINTS WHERE TABLE_NAME = 'MT_CONTRATO_JAVA' AND CONSTRAINT_TYPE = 'U';
