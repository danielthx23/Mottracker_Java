-- Debug: Verificar os valores atuais de estado_moto
SELECT estado_moto, COUNT(*) as quantidade 
FROM MT_MOTO_JAVA 
GROUP BY estado_moto;

-- Verificar se há valores nulos
SELECT COUNT(*) as total_motos, 
       COUNT(estado_moto) as motos_com_estado,
       COUNT(*) - COUNT(estado_moto) as motos_sem_estado
FROM MT_MOTO_JAVA;

-- Atualizar motos sem estado para NO_PATIO
UPDATE MT_MOTO_JAVA 
SET estado_moto = 'NO_PATIO' 
WHERE estado_moto IS NULL;

-- Verificar se há valores numéricos que precisam ser convertidos
SELECT estado_moto, COUNT(*) as quantidade 
FROM MT_MOTO_JAVA 
WHERE estado_moto IN ('0', '1', '2', '3', '4')
GROUP BY estado_moto;

-- Converter valores numéricos para strings do enum
UPDATE MT_MOTO_JAVA 
SET estado_moto = 'RETIRADA' 
WHERE estado_moto = '0';

UPDATE MT_MOTO_JAVA 
SET estado_moto = 'NO_PATIO' 
WHERE estado_moto = '1';

UPDATE MT_MOTO_JAVA 
SET estado_moto = 'NO_PATIO_ERRADO' 
WHERE estado_moto = '2';

UPDATE MT_MOTO_JAVA 
SET estado_moto = 'NAO_DEVOLVIDA' 
WHERE estado_moto = '3';

-- Verificar resultado final
SELECT estado_moto, COUNT(*) as quantidade 
FROM MT_MOTO_JAVA 
GROUP BY estado_moto;
