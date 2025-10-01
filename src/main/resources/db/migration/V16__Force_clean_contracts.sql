-- Verificar e limpar completamente registros duplicados de contratos
-- Primeiro, vamos ver quantos registros temos
-- SELECT COUNT(*) FROM MT_CONTRATO_JAVA;

-- Remover TODOS os registros duplicados baseado em múltiplos critérios
DELETE FROM MT_CONTRATO_JAVA 
WHERE ID_CONTRATO IN (
    SELECT ID_CONTRATO 
    FROM (
        SELECT ID_CONTRATO,
               ROW_NUMBER() OVER (
                   PARTITION BY USUARIO_CONTRATO_ID, MOTO_CONTRATO_ID, 
                   DATA_DE_ENTRADA_CONTRATO, VALOR_TORAL_CONTRATO
                   ORDER BY ID_CONTRATO DESC
               ) as rn
        FROM MT_CONTRATO_JAVA
    ) 
    WHERE rn > 1
);

-- Se ainda houver duplicatas, remover baseado apenas em usuário e moto
DELETE FROM MT_CONTRATO_JAVA 
WHERE ID_CONTRATO IN (
    SELECT ID_CONTRATO 
    FROM (
        SELECT ID_CONTRATO,
               ROW_NUMBER() OVER (
                   PARTITION BY USUARIO_CONTRATO_ID, MOTO_CONTRATO_ID 
                   ORDER BY ID_CONTRATO
               ) as rn
        FROM MT_CONTRATO_JAVA
    ) 
    WHERE rn > 1
);

-- Verificar se ainda há duplicatas por ID
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

-- Resetar a sequência para evitar conflitos futuros
-- Primeiro, vamos encontrar o próximo ID disponível
-- SELECT MAX(ID_CONTRATO) + 1 FROM MT_CONTRATO_JAVA;

-- Recriar a sequência se necessário (Oracle)
-- DROP SEQUENCE MT_CONTRATO_JAVA_SEQ;
-- CREATE SEQUENCE MT_CONTRATO_JAVA_SEQ START WITH (SELECT MAX(ID_CONTRATO) + 1 FROM MT_CONTRATO_JAVA);
