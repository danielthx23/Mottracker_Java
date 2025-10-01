-- Deletar TODOS os registros da tabela de contratos
DELETE FROM MT_CONTRATO_JAVA;

-- Resetar a sequência para começar do 1
-- Primeiro, vamos verificar se a sequência existe
-- SELECT * FROM USER_SEQUENCES WHERE SEQUENCE_NAME LIKE '%CONTRATO%';

-- Se a sequência existir, vamos resetá-la
-- ALTER SEQUENCE MT_CONTRATO_JAVA_SEQ RESTART START WITH 1;

-- Se não existir, vamos criá-la
-- CREATE SEQUENCE MT_CONTRATO_JAVA_SEQ START WITH 1 INCREMENT BY 1;

-- Verificar se a tabela está vazia
-- SELECT COUNT(*) FROM MT_CONTRATO_JAVA;
