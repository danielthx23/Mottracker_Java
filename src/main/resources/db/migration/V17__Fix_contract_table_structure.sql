-- Verificar e corrigir a estrutura da tabela de contratos
-- Primeiro, vamos garantir que não há registros com ID duplicado
DELETE FROM MT_CONTRATO_JAVA 
WHERE ID_CONTRATO IN (
    SELECT ID_CONTRATO 
    FROM (
        SELECT ID_CONTRATO,
               ROW_NUMBER() OVER (
                   PARTITION BY ID_CONTRATO 
                   ORDER BY ID_CONTRATO
               ) as rn
        FROM MT_CONTRATO_JAVA
    ) 
    WHERE rn > 1
);

-- Verificar se há algum problema com a sequência
-- Vamos recriar a sequência para garantir que está funcionando
-- Primeiro, vamos verificar se a sequência existe
-- SELECT * FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'MT_CONTRATO_JAVA_SEQ';

-- Se a sequência não existir, vamos criá-la
-- CREATE SEQUENCE MT_CONTRATO_JAVA_SEQ START WITH 1 INCREMENT BY 1;

-- Vamos também verificar se há algum problema com a chave primária
-- ALTER TABLE MT_CONTRATO_JAVA DROP CONSTRAINT SYS_C0012345; -- Substitua pelo nome real da constraint
-- ALTER TABLE MT_CONTRATO_JAVA ADD CONSTRAINT PK_MT_CONTRATO_JAVA PRIMARY KEY (ID_CONTRATO);

-- Verificar se há índices duplicados ou problemáticos
-- DROP INDEX IF EXISTS IDX_CONTRATO_USUARIO;
-- DROP INDEX IF EXISTS IDX_CONTRATO_MOTO;

-- Recriar índices se necessário
-- CREATE INDEX IDX_CONTRATO_USUARIO ON MT_CONTRATO_JAVA(USUARIO_CONTRATO_ID);
-- CREATE INDEX IDX_CONTRATO_MOTO ON MT_CONTRATO_JAVA(MOTO_CONTRATO_ID);
