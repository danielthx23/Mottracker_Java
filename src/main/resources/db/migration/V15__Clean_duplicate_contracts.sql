-- Remove contratos duplicados mantendo apenas o mais recente
DELETE FROM MT_CONTRATO_JAVA 
WHERE ID_CONTRATO IN (
    SELECT ID_CONTRATO 
    FROM (
        SELECT ID_CONTRATO,
               ROW_NUMBER() OVER (
                   PARTITION BY USUARIO_CONTRATO_ID, MOTO_CONTRATO_ID, DATA_DE_ENTRADA_CONTRATO 
                   ORDER BY ID_CONTRATO DESC
               ) as rn
        FROM MT_CONTRATO_JAVA
    ) 
    WHERE rn > 1
);

-- Verificar se ainda há duplicatas
-- Se houver, remover todas as duplicatas exceto a primeira
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
